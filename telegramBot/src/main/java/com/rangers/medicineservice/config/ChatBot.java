package com.rangers.medicineservice.config;


import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.service.impl.CartItemServiceImpl;
import com.rangers.medicineservice.service.impl.MedicineServiceImpl;
import com.rangers.medicineservice.service.impl.ScheduleServiceImpl;
import com.rangers.medicineservice.service.impl.UserServiceImpl;
import com.rangers.medicineservice.utils.GetButtons;
import com.rangers.medicineservice.utils.RegistrationUser;
import com.rangers.medicineservice.utils.headers.MenuHeader;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ChatBot extends TelegramLongPollingBot {
    private final GetButtons getButtons;
    private final RegistrationUser registrationUser;
    private final UserServiceImpl userService;
    private final ScheduleServiceImpl scheduleService;
    private final Map<String, UserRegistrationDto> users = new HashMap<>();
    private final Map<String, Integer> registrationStep = new HashMap<>();
    public Map<String, Boolean> isRegistrationInProgress = new HashMap<>();
    public Map<String, Boolean> addToCart = new ConcurrentHashMap<>();
    public Map<String, MedicineDto> medicineNameForCart = new ConcurrentHashMap<>();
    public Map<String, String> doctorId = new HashMap<>();
    public Map<String, String> dateSchedule = new HashMap<>();
    public Map<String, String> timeSchedule = new HashMap<>();
    public Map<String, Integer> lastMessageId = new HashMap<>();

    private final BotConfig config;
    private final MedicineServiceImpl medicineService;
    private final CartItemServiceImpl cartItemService;

    public ChatBot(@Value("${bot.token}") String botToken, GetButtons getButtons, RegistrationUser registrationUser,
                   UserServiceImpl userService, BotConfig config,
                   ScheduleServiceImpl scheduleService, MedicineServiceImpl medicineService, CartItemServiceImpl cartItemService) {
        super(botToken);
        this.getButtons = getButtons;
        this.registrationUser = registrationUser;
        this.userService = userService;
        this.config = config;
        this.scheduleService = scheduleService;
        this.medicineService = medicineService;
        this.cartItemService = cartItemService;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

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

        switch (messageText) {
            case "/start":
                sendMenu(chatId, GetButtons.getListsStartMenu(), MenuHeader.CHOOSE_ACTION);
                break;
            case "/menu":
                sendMsg(chatId, "отправка меню с доп функциями(например: мои рецепты, мои заказы)");
            default:
                if (isRegistrationInProgress.getOrDefault(chatId, false)) {
                    handleRegistration(messageText, chatId);
                } else if (addToCart.getOrDefault(chatId, false)) {
                   handleQuantity(messageText, chatId, medicineNameForCart.get(chatId));
                } else if (update.getMessage().hasLocation()) {
                    processLocation(update);
                }
                break;
        }
    }

    private void handleCallbackQuery(Update update) {
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        String callbackData = update.getCallbackQuery().getData();

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
        } else if (callbackData.startsWith("category:")) {
            handleMedicineCategoryCallback(chatId, callbackData);
        } else if (callbackData.startsWith("medicine:")) {
            handleMedicineCallback(chatId, callbackData);
        } else if (callbackData.startsWith("categoryButtons")){
            handleCategoryCallback(chatId, callbackData);
        } else if (callbackData.startsWith("to cart")) {
            handleToCartCallBack(chatId, callbackData);
        } else if (callbackData.startsWith("delete item")) {
            handleDeleteItemCallBack(chatId, callbackData);
        } else {
            handleDefaultCallback(chatId, callbackData);
        }
    }

    private void handleSpecializationCallback(String chatId, String callbackData) {
        String specializationName = callbackData.substring("specialization:".length());
        sendMenu(chatId, GetButtons.getListsDoctors(specializationName), MenuHeader.CHOOSE_DOCTOR);
    }

    private void handleDoctorCallback(String chatId, String callbackData) {
        doctorId.put(chatId, callbackData.substring("Doctor:".length()));
        sendMenu(chatId, GetButtons.getListsDatesByDoctor(doctorId.get(chatId)), MenuHeader.CHOOSE_DATE);
    }

    private void handleDateCallback(String chatId, String callbackData) {
        dateSchedule.put(chatId, callbackData.substring("Date:".length()));
        sendMenu(chatId, GetButtons.getListsTimesByDoctorAndDate(doctorId.get(chatId), dateSchedule.get(chatId)),
                MenuHeader.CHOOSE_TIME);
    }

    private void handleTimeCallback(String chatId, String callbackData) {
        timeSchedule.put(chatId, callbackData.substring("Time:".length()));
        sendMenu(chatId, getButtons.getListsScheduleType(), MenuHeader.CHOOSE_APPOINTMENT_TYPE);
    }

    private void handleTypeCallback(String chatId, String callbackData) {
        ScheduleFullDto scheduleFullDto = scheduleService.getSchedule(UUID.fromString(doctorId.get(chatId)),
                dateSchedule.get(chatId) + " " + timeSchedule.get(chatId) + ":00");
        CreateVisitRequestDto createVisitRequestDto = new CreateVisitRequestDto();
        createVisitRequestDto.setUser_id(userService.getUserIdByChatId(chatId));
        createVisitRequestDto.setAppointmentType(callbackData.substring("type:".length()));
        CreateVisitResponseDto responseDto = scheduleService.createVisit(
                String.valueOf(scheduleFullDto.getScheduleId()), createVisitRequestDto);
        sendMsg(chatId, "You have signed up for: " + responseDto.getDoctorName() + "\n"
                + "Date and time: " + responseDto.getDateTime() + "\n"
                + responseDto.getLinkOrAddress());
        sendMenu(chatId, GetButtons.getListsStartMenu(), MenuHeader.CHOOSE_ACTION);
    }

    private void handleDefaultCallback(String chatId, String callbackData) {
        switch (callbackData) {
            case "start1":
                handleStart1(chatId);
                break;
            case "start2":
                handleStart2(chatId);
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
            users.put(chatId, new UserRegistrationDto());
            registrationStep.put(chatId, 0);
            startRegistration(chatId);
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

    public void sendMenu(String chatId, List<List<InlineKeyboardButton>> rowsInline, String header) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(header);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        markupKeyboard.setKeyboard(rowsInline);
        message.setReplyMarkup(markupKeyboard);

        try {
            Message sentMessage = execute(message);
            int messageId = sentMessage.getMessageId();
            if (lastMessageId.get(chatId) != null) {
                deleteMessage(chatId, lastMessageId.get(chatId));
            }
            lastMessageId.put(chatId, messageId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
                sendMenu(chatId, getButtons.getListsStartMenu, MenuHeader.CHOOSE_ACTION);
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
        } catch (TelegramApiException ignored) {

        }
    }

    private void handleStart2(String chatId) {
        if (!registrationUser.isHaveUser(chatId)){
            users.put(chatId, new UserRegistrationDto());
            registrationStep.put(chatId, 0);
            startRegistration(chatId);
        }
        //проверка на наличие рецептов в базе
        List<PrescriptionDto> prescriptions = userService.getUserPrescriptions(UUID.fromString(userService.getUserIdByChatId(chatId)));
        if (prescriptions.size() == 1) {
            sendMsg(chatId, "You have 1 prescription. Would you like to stock your recipe?");
            sendMenu(chatId, GetButtons.getYesNoButtons("Yes", "No","stock the prescription",
                    "do not stock the prescription"), MenuHeader.CHOOSE_ACTION);
        } else if (prescriptions.size() > 1) {
            sendMsg(chatId, "You have " + prescriptions.size() + " prescriptions. Would you like to stock your recipes?");
            sendMenu(chatId, GetButtons.getYesNoButtons("Yes", "No", "choose the prescription",
                    "do not stock the prescription"), MenuHeader.CHOOSE_ACTION);
        } else {
            sendMenu(chatId, GetButtons.getMedicineCategoryButtons(), MenuHeader.CHOOSE_MEDICINE_CATEGORY);
        }
    }

    private void handleMedicineCategoryCallback(String chatId, String callbackData) {
        String categoryName = callbackData.substring("category:".length());
        sendMenu(chatId, GetButtons.getListsMedicines(categoryName), MenuHeader.CHOOSE_MEDICINE);
    }

    private void handleMedicineCallback(String chatId, String callbackData){
        String medicineName = callbackData.substring("medicine:".length());
        MedicineDto medicineDto = medicineService.getByName(medicineName);
        sendMsg(chatId, "Medicine: " + medicineDto.getName() + "\n"
                + "Description: " + medicineDto.getDescription() + "\n"
                + "Price: " + medicineDto.getPrice());
        sendMsg(chatId,"Enter quantity");
        addToCart.put(chatId, true);
        medicineNameForCart.put(chatId, medicineDto);
    }

    private void handleQuantity(String messageText, String chatId, MedicineDto medicineDto){
        String input = messageText.trim();
        if (!input.matches("^[1-9]\\d*$")) {
            sendMsg(chatId, "Please, enter a valid value");
        }
        int quantity = Integer.parseInt(input);

        CartItemBeforeCreationDto cartItemBeforeCreationDto = new CartItemBeforeCreationDto(
                medicineDto.getId(),
                userService.getUserIdByChatId(chatId),
                quantity);
        cartItemService.createCartItem(cartItemBeforeCreationDto);
        sendMsg(chatId, "Item added to cart");
//        sendMsg(chatId, "Do you want to select more products?");
        sendMenu(chatId, GetButtons.getYesNoButtons(
                "Yes",
                "Go to cart",
                "categoryButtons",
                "to cart"
        ), MenuHeader.SELECT_MORE_PRODUCTS);
    }

    private void handleCategoryCallback(String chatId, String callbackData) {
        sendMenu(chatId, GetButtons.getMedicineCategoryButtons(), MenuHeader.CHOOSE_MEDICINE_CATEGORY);
    }

    private void handleToCartCallBack(String chatId, String callbackData){
        String userId = userService.getUserIdByChatId(chatId);
        sendMenu(chatId, GetButtons.getCart(userId), MenuHeader.CHOOSE_ITEM_FOR_DELETE);
    }

    private void handleDeleteItemCallBack(String chatId, String callbackData){
        sendMsg(chatId, "Hello");
    }

}
