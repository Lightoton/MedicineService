package com.rangers.medicineservice.config;


import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.mapper.ScheduleMapper;
import com.rangers.medicineservice.service.impl.ScheduleServiceImpl;
import com.rangers.medicineservice.service.impl.UserServiceImpl;
import com.rangers.medicineservice.utils.GetButtons;
import com.rangers.medicineservice.utils.RegistrationUser;
import com.rangers.medicineservice.utils.formater.ScheduleFormat;
import com.rangers.medicineservice.utils.SupportMailSender;
import com.rangers.medicineservice.utils.headers.MenuHeader;
import com.rangers.medicineservice.utils.userVariable.UserVariable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ChatBot extends TelegramLongPollingBot {
    private final RegistrationUser registrationUser;
    private final UserServiceImpl userService;
    private final ScheduleServiceImpl scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleFormat scheduleFormat;
    private final Map<String, UserVariable> userVariableMap = new ConcurrentHashMap<>();

    private final SupportMailSender supportMainSender;
    private final BotConfig config;

    public ChatBot(@Value("${bot.token}") String botToken, RegistrationUser registrationUser,
                   UserServiceImpl userService, BotConfig config,
                   ScheduleServiceImpl scheduleService, SupportMailSender supportMainSender, ScheduleMapper scheduleMapper
            , ScheduleFormat scheduleFormat) {
        super(botToken);
        this.registrationUser = registrationUser;
        this.userService = userService;
        this.config = config;
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
        this.scheduleFormat = scheduleFormat;
        this.supportMainSender = supportMainSender;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Async
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleIncomingMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleIncomingMessage(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        String messageText = update.getMessage().getText();
        userVariableMap.computeIfAbsent(chatId, k -> new UserVariable());
        try {
            userVariableMap.get(chatId).setUserId(userService.getUserIdByChatId(chatId));
        } catch (Exception ignored) {

        }


        switch (messageText) {
            case "/start":
                resettingVariables(chatId);
                sendMenu(chatId, GetButtons.getListsStartMenu(), MenuHeader.CHOOSE_ACTION);
                break;
            case "/menu":
                sendMsg(chatId, "отправка меню с доп функциями(например: мои рецепты, мои заказы)");
                break;
            case "/support":
                userVariableMap.get(chatId).setIsSupportInProgress(true);
                sendMsg(chatId, "Thank you for contacting our support. Please describe your problem or" +
                        " question as in more detail.");
                break;
            case "/my_appointment":
                handleScheduleByUser(chatId, userVariableMap.get(chatId).getUserId());
                break;
            default:
                if (userVariableMap.get(chatId).getIsRegistrationInProgress().equals(true)) {
                    handleRegistration(messageText, chatId);
                } else if (userVariableMap.get(chatId).getIsSupportInProgress().equals(true)) {
                    handleSupport(messageText, chatId);
                } else if (update.getMessage().hasLocation()) {
                    processLocation(update);
                }
                break;
        }
    }

    private void handleCallbackQuery(Update update) {
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        String callbackData = update.getCallbackQuery().getData();

        if (callbackData == null) {
            sendMsg(chatId, "Callback data is null.");
            return;
        }


        if (callbackData.startsWith("specialization:")) {
            handleSpecializationCallback(chatId, callbackData);
        } else if (callbackData.startsWith("Doctor:")) {
            handleDoctorCallback(chatId, callbackData);
        } else if (callbackData.startsWith("Date:")) {
            handleDateCallback(chatId, callbackData);
        } else if (callbackData.startsWith("Time:")) {
            handleTimeCallback(chatId, callbackData);
        } else if (callbackData.startsWith("type:")) {
            handleTypeCallback(chatId, callbackData);
        } else if (callbackData.startsWith("ScheduleUser:")) {
            handleScheduleBYUserCancelCallback(chatId, callbackData);
        } else if (callbackData.startsWith("CancelSchedule:")) {
            handleCancelApproveCallback(chatId, callbackData);
        } else if (callbackData.startsWith("ApproveCancel:")) {
            handelCancelAppointment(chatId, callbackData);
        } else {
            handleDefaultCallback(chatId, callbackData);
        }
    }

    private void handleSpecializationCallback(String chatId, String callbackData) {
        String specializationName = callbackData.substring("specialization:".length());
        sendMenu(chatId, GetButtons.getListsDoctors(specializationName), MenuHeader.CHOOSE_DOCTOR);
    }

    private void handleDoctorCallback(String chatId, String callbackData) {
        userVariableMap.get(chatId).setDoctorId(callbackData.substring("Doctor:".length()));
        try {
            sendMenu(chatId, GetButtons.getListsDatesByDoctor(userVariableMap.get(chatId).getDoctorId()),
                    MenuHeader.CHOOSE_DATE);
        } catch (Exception e) {
            sendMsg(chatId, "There are no appointments for this doctor, please try again later!");
            sendStartMenu(chatId);
        }

    }

    private void handleDateCallback(String chatId, String callbackData) {
        userVariableMap.get(chatId).setDateSchedule(callbackData.substring("Date:".length()));
        sendMenu(chatId, GetButtons.getListsTimesByDoctorAndDate(userVariableMap.get(chatId).getDoctorId(),
                        userVariableMap.get(chatId).getDateSchedule()),
                MenuHeader.CHOOSE_TIME);
    }

    private void handleTimeCallback(String chatId, String callbackData) {
        userVariableMap.get(chatId).setTimeSchedule(callbackData.substring("Time:".length()));
        sendMenu(chatId, GetButtons.getListsScheduleType(), MenuHeader.CHOOSE_APPOINTMENT_TYPE);
    }

    private void handleTypeCallback(String chatId, String callbackData) {
        ScheduleFullDto scheduleFullDto = scheduleService.getSchedule(UUID.fromString(userVariableMap.
                get(chatId).getDoctorId()), userVariableMap.get(chatId).getDateSchedule()
                + " " + userVariableMap.get(chatId).getTimeSchedule() + ":00");
        CreateVisitRequestDto createVisitRequestDto = new CreateVisitRequestDto();
        createVisitRequestDto.setUser_id(userService.getUserIdByChatId(chatId));
        createVisitRequestDto.setAppointmentType(callbackData.substring("type:".length()));
        CreateVisitResponseDto responseDto = scheduleService.createVisit(
                String.valueOf(scheduleFullDto.getScheduleId()), createVisitRequestDto);
        sendMsg(chatId, "You have signed up for: " + responseDto.getDoctorName() + "\n"
                + "Date and time: " + responseDto.getDateTime() + "\n"
                + "Type of appointment: " + createVisitRequestDto.getAppointmentType() + "\n"
                + responseDto.getLinkOrAddress());
        sendStartMenu(chatId);
    }

    private void handleScheduleByUser(String chatId, String userId) {
        List<List<InlineKeyboardButton>> listsScheduleActiveByUser = GetButtons.getListsSchedulesActiveByUser(userId);
        if (listsScheduleActiveByUser.isEmpty()) {
            sendMsg(chatId, "You have no active appointments");
        } else {
            sendMenu(chatId, listsScheduleActiveByUser, MenuHeader.CHOOSE_SCHEDULE);
        }

    }

    private void handleScheduleBYUserCancelCallback(String chatId, String callbackData) {
        String scheduleId = callbackData.substring("ScheduleUser:".length());
        ScheduleFullDto scheduleFullDto = scheduleMapper.toFullDto(scheduleService.findById(UUID.fromString(scheduleId)));

        if (scheduleFullDto != null) {
            userVariableMap.get(chatId).setScheduleIdForCancel(scheduleId);
            String fullInfo = scheduleFormat.getScheduleFormat(scheduleFullDto);


            // Отправка кнопки отмены
            List<List<InlineKeyboardButton>> cancelButton = GetButtons.getCancelButtonForSchedule(scheduleId);
            sendMenu(chatId, cancelButton, fullInfo + " \n" + "Cancel the selected schedule?");
        } else {
            sendMsg(chatId, "Schedule not found.");
        }
    }

    private void handleCancelApproveCallback(String chatId, String callbackData) {
        // Отправка кнопок подтверждения
        List<List<InlineKeyboardButton>> cancelButton = GetButtons.getApproveCancelButtonForSchedule();
        sendMenu(chatId, cancelButton, "Are you sure you want to cancel your appointment?");

    }

    private void handleDefaultCallback(String chatId, String callbackData) {
        switch (callbackData) {
            case "start1":
                handleStart1(chatId);
                break;
            case "start2":
                sendMsg(chatId, "Будет выполняться алгоритм для поиска и возможной покупки лекарств в аптеке");
                break;
            case "start3":
                sendMsg(chatId, "Общение с AI");
                break;
            default:
                break;
        }
    }

    private void handleStart1(String chatId) {
        if (registrationUser.isHaveUser(chatId)) {
            sendMenu(chatId, GetButtons.getListsSchedule(), MenuHeader.CHOOSE_SPECIALIZATION);
        } else {
            userVariableMap.get(chatId).setUserRegistrationDto(new UserRegistrationDto());
            userVariableMap.get(chatId).setRegistrationStep(0);
            startRegistration(chatId);
        }
    }

    private void handleSupport(String messageText, String chatId) {
        supportMainSender.send("", "", messageText + ". " + "UserChatId: " + chatId);
        sendMsg(chatId, "Thank you for contacting our support. We have received your message and will get back to you as soon as possible.");
        userVariableMap.get(chatId).setIsSupportInProgress(false);
    }

    private void handelCancelAppointment(String chatId, String callbackData) {
        String response = callbackData.substring("ApproveCancel:".length());
        CancelVisitRequestDto cancelVisitRequestDto = new CancelVisitRequestDto();
        cancelVisitRequestDto.setUserId(userVariableMap.get(chatId).getUserId());
        if (response.equals("yes")) {
            CancelVisitResponseDto responseDto = scheduleService.
                    cancelVisit(userVariableMap.get(chatId).getScheduleIdForCancel(), cancelVisitRequestDto);
            sendMsg(chatId, scheduleFormat.getCancelScheduleFormat(responseDto));
            userVariableMap.get(chatId).setScheduleIdForCancel(null);
            sendStartMenu(chatId);
        } else {
            sendStartMenu(chatId);
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

    private void sendMenu(String chatId, List<List<InlineKeyboardButton>> rowsInline, String header) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(header);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        markupKeyboard.setKeyboard(rowsInline);
        message.setReplyMarkup(markupKeyboard);

        try {
            Message sentMessage = execute(message);
            int messageId = sentMessage.getMessageId();
            if (userVariableMap.get(chatId).getLastMessageId() != null) {
                deleteMessage(chatId, userVariableMap.get(chatId).getLastMessageId());
            }
            userVariableMap.get(chatId).setLastMessageId(messageId);
        } catch (TelegramApiException ignored) {

        }
    }


    private void sendMsg(String chatId, String text) {
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
        switch (userVariableMap.get(chatId).getRegistrationStep()) {
            case 0:
                userVariableMap.get(chatId).getUserRegistrationDto().setFirstname(messageText);
                sendMsg(chatId, "Great! Now enter your last name:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 1:
                userVariableMap.get(chatId).getUserRegistrationDto().setLastname(messageText);
                sendMsg(chatId, "Great! Now enter your email:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
                break;
            case 2:
                userVariableMap.get(chatId).getUserRegistrationDto().setEmail(messageText);
                sendMsg(chatId, "Great! Now enter your phone number:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
                break;
            case 3:
                userVariableMap.get(chatId).getUserRegistrationDto().setPhoneNumber(messageText);
                sendMsg(chatId, "Great! Now enter your address:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
                break;
            case 4:
                userVariableMap.get(chatId).getUserRegistrationDto().setAddress(messageText);
                sendMsg(chatId, "Great! Now enter your city of residence:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
                break;
            case 5:
                userVariableMap.get(chatId).getUserRegistrationDto().setCity(messageText);
                sendMsg(chatId, "Great! Now enter your country of residence:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
                break;
            case 6:
                userVariableMap.get(chatId).getUserRegistrationDto().setCountry(messageText);
                sendMsg(chatId, "Great! Now enter your postal code:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
                break;
            case 7:
                userVariableMap.get(chatId).getUserRegistrationDto().setPostalCode(messageText);
                sendMsg(chatId, "Great! Now enter your insurance number:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep()+1);
                break;
            case 8:
                userVariableMap.get(chatId).getUserRegistrationDto().setPolicyNumber(messageText);
                userVariableMap.get(chatId).getUserRegistrationDto().setChatId(chatId);
                sendMsg(chatId, "Great! Registration is completed!!!");
                userService.createUser(userVariableMap.get(chatId).getUserRegistrationDto());
                userVariableMap.get(chatId).setIsRegistrationInProgress(false);
                sendStartMenu(chatId);
                userVariableMap.get(chatId).setUserId(userService.getUserIdByChatId(chatId));
                break;
        }

    }

    private void startRegistration(String chatId) {
        sendMsg(chatId, "Hello! Let's start registration. Enter your name:");
        userVariableMap.get(chatId).setIsRegistrationInProgress(true);
    }

    private void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);

        try {
            execute(deleteMessage);
        } catch (TelegramApiException ignored) {

        }
    }

    private void resettingVariables(String chatId) {
            userVariableMap.get(chatId).setIsRegistrationInProgress(false);
            userVariableMap.get(chatId).setIsSupportInProgress(false);
            userVariableMap.get(chatId).setRegistrationStep(null);
            userVariableMap.get(chatId).setScheduleIdForCancel(null);
    }

    private void sendStartMenu(String chatId) {
        sendMenu(chatId, GetButtons.getListsStartMenu(), MenuHeader.CHOOSE_ACTION);
    }
}
