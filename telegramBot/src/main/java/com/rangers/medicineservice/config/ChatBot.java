package com.rangers.medicineservice.config;


import com.rangers.medicineservice.dto.CreateVisitRequestDto;
import com.rangers.medicineservice.dto.CreateVisitResponseDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.dto.UserRegistrationDto;
import com.rangers.medicineservice.service.ZoomMeetingService;
import com.rangers.medicineservice.service.impl.ScheduleServiceImpl;
import com.rangers.medicineservice.service.impl.UserServiceImpl;
import com.rangers.medicineservice.utils.GetButtons;
import com.rangers.medicineservice.utils.RegistrationUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
public class ChatBot extends TelegramLongPollingBot {
    private static int lastMessageId;
    private final GetButtons getButtons;
    private final RegistrationUser registrationUser;
    private final ZoomMeetingService zoomMeetingService;
    private final UserServiceImpl userService;
    private final ScheduleServiceImpl scheduleService;
    private final Map<String, UserRegistrationDto> users = new HashMap<>();
    private final Map<String, Integer> registrationStep = new HashMap<>();
    public Map<String, Boolean> isRegistrationInProgress = new HashMap<>();
    public Map<String, String> doctorId = new HashMap<>();
    public Map<String, String> dateSchedule = new HashMap<>();
    public Map<String, String> timeSchedule = new HashMap<>();

    private final BotConfig config;

    public ChatBot(@Value("${bot.token}") String botToken, GetButtons getButtons, RegistrationUser registrationUser,
                   UserServiceImpl userService, BotConfig config, ZoomMeetingService zoomMeetingService,
                   ScheduleServiceImpl scheduleService) {
        super(botToken);
        this.getButtons = getButtons;
        this.registrationUser = registrationUser;
        this.userService = userService;
        this.config = config;
        this.zoomMeetingService = zoomMeetingService;
        this.scheduleService = scheduleService;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }


    @Override
    public void onUpdateReceived(Update update) {

        LocalDateTime dateTime = LocalDateTime.now();
        String startTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";

        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String messageText = update.getMessage().getText();

            if (messageText.equals("/start")) {
                sendMenu(chatId, getButtons.getListsStartMenu); // Send menu for User
            } else if (update.hasMessage() && update.getMessage().hasLocation()) {
                // if we get users location
                processLocation(update);
            } else if (isRegistrationInProgress.get(chatId)) {
                handleRegistration(messageText, chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());

            if (callbackData.startsWith("specialization:")) {
                String specializationName = callbackData.substring("specialization:".length());
                sendMenu(chatId, GetButtons.getListsDoctors(specializationName));
            } else if (callbackData.startsWith("Doctor:")) {
                doctorId.put(chatId, callbackData.substring("Doctor:".length()));
                sendMenu(chatId, GetButtons.getListsDatesByDoctor(doctorId.get(chatId)));
            } else if (callbackData.startsWith("Date:")) {
                dateSchedule.put(chatId, callbackData.substring("Date:".length()));
                sendMenu(chatId, GetButtons.getListsTimesByDoctorAndDate(doctorId.get(chatId), dateSchedule.get(chatId)));
            } else if (callbackData.startsWith("Time:")) {
                timeSchedule.put(chatId, callbackData.substring("Time:".length()));
                sendMenu(chatId, getButtons.getListsScheduleType);
            } else if (callbackData.startsWith("type:")) {
                ScheduleFullDto scheduleFullDto = scheduleService.getSchedule(UUID.fromString(doctorId.get(chatId)),
                        dateSchedule.get(chatId) + " " + timeSchedule.get(chatId) + ":00");
                CreateVisitRequestDto createVisitRequestDto = new CreateVisitRequestDto();
                createVisitRequestDto.setUser_id(userService.getUserIdByChatId(chatId));
                createVisitRequestDto.setAppointmentType(callbackData.substring("type:".length()));
                CreateVisitResponseDto responseDto =  scheduleService.createVisit(String.valueOf(scheduleFullDto.getScheduleId()), createVisitRequestDto);
                sendMsg(chatId, "You have signed up for: " + responseDto.getDoctorName() + "\n"
                        + "Date and time: " + responseDto.getDateTime() + "\n"
                        + responseDto.getLinkOrAddress());
                sendMenu(chatId,getButtons.getListsStartMenu);
            }
            switch (callbackData) {
                case "start1":
                    if (registrationUser.isHaveUser(chatId)) {
                        sendMenu(chatId, getButtons.getListsSchedule);
                    } else {
                        users.put(chatId, new UserRegistrationDto());
                        registrationStep.put(chatId, 0);
                        startRegistration(chatId);
                    }
                    break;
                case "start2":
                    sendMsg(chatId, "Будет выполняться алгоритм для поиска и возможной покупки лекарств в аптеке");
                    break;
                case "start3":
                    sendMsg(chatId, zoomMeetingService.createZoomMeeting(startTime));
                    break;
                default:
                    break;

            }
        }
    }


    private void sendDataBot(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(getBotUsername());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMenu(String chatId, List<List<InlineKeyboardButton>> rowsInline) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Choose an action:");

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        markupKeyboard.setKeyboard(rowsInline);
        message.setReplyMarkup(markupKeyboard);

        try {
            Message sentMessage = execute(message);
            int messageId = sentMessage.getMessageId();
            if (lastMessageId != 0) {
                deleteMessage(chatId, lastMessageId);
            }
            lastMessageId = messageId;
        } catch (TelegramApiException ignored) {

        }
    }


    public void sendMsg(String chatId, String text) {
        if (text != null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    // todo
    // get Users location
    private void processLocation(Update update) {
        Message message = update.getMessage();
        Location location = message.getLocation();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String chatId = String.valueOf(message.getChatId());

    }

    private void handleRegistration(String messageText, String chatId) {
        switch (registrationStep.get(chatId)) {
            case 0:
                users.get(chatId).setFirstname(messageText);
                sendMsg(chatId, "Great! Now enter your last name:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                if (lastMessageId != 0) {
                    deleteMessage(chatId, lastMessageId);
                }
                break;
            case 1:
                users.get(chatId).setLastname(messageText);
                sendMsg(chatId, "Great! Now enter your email:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 2:
                users.get(chatId).setEmail(messageText);
                sendMsg(chatId, "Great! Now enter your phone number:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 3:
                users.get(chatId).setPhoneNumber(messageText);
                sendMsg(chatId, "Great! Now enter your address:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 4:
                users.get(chatId).setAddress(messageText);
                sendMsg(chatId, "Great! Now enter your city of residence:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 5:
                users.get(chatId).setCity(messageText);
                sendMsg(chatId, "Great! Now enter your country of residence:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 6:
                users.get(chatId).setCountry(messageText);
                sendMsg(chatId, "Great! Now enter your postal code:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 7:
                users.get(chatId).setPostalCode(messageText);
                sendMsg(chatId, "Great! Now enter your insurance number:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 8:
                users.get(chatId).setPolicyNumber(messageText);
                users.get(chatId).setChatId(chatId);
                sendMsg(chatId, "Great! Registration is completed!!!");
                userService.createUser(users.get(chatId));
                isRegistrationInProgress.put(chatId, false);
                sendMenu(chatId, getButtons.getListsStartMenu);
                break;
        }

    }

    public void startRegistration(String chatId) {
        sendMsg(chatId, "Hello! Let's start registration. Enter your name:");
        isRegistrationInProgress.put(chatId, true);
    }

    private void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);

        try {
            execute(deleteMessage);
        } catch (TelegramApiException ignored){

        }
    }
}
