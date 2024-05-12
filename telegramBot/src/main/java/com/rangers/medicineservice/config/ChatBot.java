package com.rangers.medicineservice.config;

//import com.rangers.medicineservice.service.ZoomMeetingService;
//import org.springframework.beans.factory.annotation.Autowired;

import com.rangers.medicineservice.dto.UserRegistrationDto;
import com.rangers.medicineservice.service.ZoomMeetingService;
import com.rangers.medicineservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ChatBot extends TelegramLongPollingBot {

        @Autowired
    private ZoomMeetingService zoomMeetingService;
    private final UserServiceImpl userService;
    private Map<String, UserRegistrationDto> users = new HashMap<>();
    private Map<String, Integer> registrationStep = new HashMap<>();
    private Map<String, Boolean> isRegistrationInProgress = new HashMap<>();

    private final BotConfig config;

    public ChatBot(@Value("${bot.token}") String botToken, UserServiceImpl userService, BotConfig config) {
        super(botToken);
        this.userService = userService;
        this.config = config;
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
                sendMenu(chatId); // Send menu for User
            } else if (update.hasMessage() && update.getMessage().hasLocation()) {
                // if we get users location
                processLocation(update);
            } else if (isRegistrationInProgress.get(chatId)) {
                handleRegistration(messageText, chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            switch (callbackData) {
                case "command1":
                    if (isHaveUser(chatId)) {
                        sendMsg(chatId, "Будет выполняться алгоритм записи к врачу");
                    } else {
                        users.put(chatId, new UserRegistrationDto());
                        registrationStep.put(chatId, 0);
                        startRegistration(chatId);
                    }
                    break;
                case "command2":
                    sendMsg(chatId, "Будет выполняться алгоритм для поиска и возможной покупки лекарств в аптеке");
                    break;
                case "command3":
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

    private void sendMenu(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите команду:");

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        // Create buttons
        List<List<InlineKeyboardButton>> rowsInline = getLists();

        markupKeyboard.setKeyboard(rowsInline);
        message.setReplyMarkup(markupKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static List<List<InlineKeyboardButton>> getLists() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Make an appointment with a doctor");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Pharmacy");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Get ZoomLink");
        button1.setCallbackData("command1");
        button2.setCallbackData("command2");
        button3.setCallbackData("command3");
        rowInline.add(button1);
        rowInline.add(button2);
        rowInline.add(button3);
        rowsInline.add(rowInline);
        return rowsInline;
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

    private boolean isHaveUser(String chatId) {
        try {
            return userService.getUserIdByChatId(chatId) != null;
        } catch (Exception e) {
            return false;
        }
    }

    private void handleRegistration(String messageText, String chatId) {
        switch (registrationStep.get(chatId)) {
            case 0:
                users.get(chatId).setFirstname(messageText);
                sendMsg(chatId, "Отлично! Теперь введи свою фамилию:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 1:
                users.get(chatId).setLastname(messageText);
                sendMsg(chatId, "Отлично! Теперь введи свою электронную почту:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 2:
                users.get(chatId).setEmail(messageText);
                sendMsg(chatId, "Отлично! Теперь введи свой номер телефона:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 3:
                users.get(chatId).setPhoneNumber(messageText);
                sendMsg(chatId, "Отлично! Теперь введи свой адресс:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 4:
                users.get(chatId).setAddress(messageText);
                sendMsg(chatId, "Отлично! Теперь введи город проживания:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 5:
                users.get(chatId).setCity(messageText);
                sendMsg(chatId, "Отлично! Теперь введи страну проживания:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 6:
                users.get(chatId).setCountry(messageText);
                sendMsg(chatId, "Отлично! Теперь введи почтовый индекс:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 7:
                users.get(chatId).setPostalCode(messageText);
                sendMsg(chatId, "Отлично! Теперь введи номер страховки:");
                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
                break;
            case 8:
                users.get(chatId).setPolicyNumber(messageText);
                users.get(chatId).setChatId(chatId);
                sendMsg(chatId, "Отлично! Регестрация завершена!!!");
                userService.createUser(users.get(chatId));
                isRegistrationInProgress.put(chatId,false);
                break;
            // обработка остальных шагов регистрации
        }

    }

    private void startRegistration(String chatId) {
        sendMsg(chatId, "Привет! Давай начнем регистрацию. Введи свое имя:");
        isRegistrationInProgress.put(chatId, true);
    }

}
