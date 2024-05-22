package com.rangers.medicineservice.config;

import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.mapper.ScheduleMapper;
import com.rangers.medicineservice.service.impl.*;
import com.rangers.medicineservice.utils.GetBotInfo;
import com.rangers.medicineservice.utils.GetButtons;
import com.rangers.medicineservice.utils.RegistrationUser;
import com.rangers.medicineservice.utils.SupportMailSender;
import com.rangers.medicineservice.utils.formater.ScheduleFormat;
import com.rangers.medicineservice.utils.headers.MenuHeader;
import com.rangers.medicineservice.utils.userVariable.UserVariable;
import lombok.extern.slf4j.Slf4j;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ChatBot extends TelegramLongPollingBot {
    private final RegistrationUser registrationUser;
    private final UserServiceImpl userService;
    private final ScheduleServiceImpl scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final ScheduleFormat scheduleFormat;
    private final SupportMailSender supportMainSender;
    private final Map<String, UserVariable> userVariableMap = new ConcurrentHashMap<>();

    private final BotConfig config;
    private final MedicineServiceImpl medicineService;
    private final CartItemServiceImpl cartItemService;
    private final PrescriptionServiceImpl prescriptionService;
    private final OrderServiceImpl orderService;

    public ChatBot(@Value("${bot.token}") String botToken, RegistrationUser registrationUser,
                   UserServiceImpl userService, BotConfig config,
                   ScheduleServiceImpl scheduleService, SupportMailSender supportMainSender, ScheduleMapper scheduleMapper
            , ScheduleFormat scheduleFormat, MedicineServiceImpl medicineService, CartItemServiceImpl cartItemService,
                   PrescriptionServiceImpl prescriptionService, OrderServiceImpl orderService) {
        super(botToken);
        this.registrationUser = registrationUser;
        this.userService = userService;
        this.config = config;
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
        this.scheduleFormat = scheduleFormat;
        this.supportMainSender = supportMainSender;
        this.medicineService = medicineService;
        this.cartItemService = cartItemService;
        this.prescriptionService = prescriptionService;
        this.orderService = orderService;
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
        } else if (update.hasMessage() && update.getMessage().hasLocation()) {
            String chatId = update.getMessage().getChatId().toString();
            handleReceivedLocation(chatId, update.getMessage().getLocation());
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
                sendStartMenu(chatId);
                break;
            case "/menu":
                sendMsg(chatId, "отправка меню с доп функциями(например: мои рецепты, мои заказы)");
                break;
            case "/location":
                sendLocationRequestButton(chatId);
                break;
            case "/support":
                userVariableMap.get(chatId).setIsSupportInProgress(true);
                sendMsg(chatId, "Thank you for contacting our support. Please describe your problem or" +
                        " question as in more detail.");
                break;
            case "/botinfo":
                sendMsg(chatId, GetBotInfo.getBotInfo()
                        , "MarkdownV2");
                break;
            case "/my_appointment":
                handleScheduleByUser(chatId, userVariableMap.get(chatId).getUserId());
                break;
            default:
                if (userVariableMap.get(chatId).getIsRegistrationInProgress().equals(true)) {
                    handleRegistration(messageText, chatId);
                } else if (userVariableMap.get(chatId).getAddToCart().equals(true)) {
                    handleQuantity(messageText, chatId, userVariableMap.get(chatId).getMedicineNameForCart());
                } else if (userVariableMap.get(chatId).getIsSupportInProgress().equals(true)) {
                    handleSupport(messageText, chatId);
                }
                break;
        }
    }

    private void sendLocationRequestButton(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Please share your location. Click the button below.");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton locationButton = new KeyboardButton("Share location");
        locationButton.setRequestLocation(true);
        row.add(locationButton);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
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
                log.info(e.getMessage());
            }
        }
    }

    private void handleReceivedLocation(String chatId, Location location) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Thank you for sharing your location!");
        // Remove the keyboard
        ReplyKeyboardRemove keyboardRemove = new ReplyKeyboardRemove();
        keyboardRemove.setRemoveKeyboard(true);
        message.setReplyMarkup(keyboardRemove);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.info(e.getMessage());
        }
        sendMsg(chatId, "Pharmacies that are near you:");
        sendMsg(chatId, pharmaciesNearby(location));
    }

    private String pharmaciesNearby(Location location) {
        if (location == null) return null;
        return "https://www.google.com/maps/search/pharmacy/@" + location.getLatitude() + "," +
                location.getLongitude() + "z/data=!3m1!4b1?entry=ttu";
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
        } else if (callbackData.startsWith("category:")) {
            handleMedicineCategoryCallback(chatId, callbackData);
        } else if (callbackData.startsWith("medicine:")) {
            handleMedicineCallback(chatId, callbackData);
        } else if (callbackData.startsWith("categoryButtons")) {
            handleCategoryCallback(chatId, callbackData);
        } else if (callbackData.startsWith("to cart")) {
            handleToCartCallBack(chatId, callbackData);
        } else if (callbackData.startsWith("delete item")) {
            handleDeleteItemCallBack(chatId, callbackData);
        } else if (callbackData.startsWith("checkout")) {
            handleCheckoutCallBack(chatId, callbackData);
        } else if (callbackData.startsWith("pickup")) {
            handlePickupCallBack(chatId);
        } else if (callbackData.startsWith("courier")) {
            handleCourierCallBack(chatId);
        } else if (callbackData.startsWith("choose the prescription")) {
            handleChoosePrescriptionCallBack(chatId);
        } else if (callbackData.startsWith("prescription:")) {
            handlePrescriptionCallBack(chatId, callbackData);
        } else if (callbackData.startsWith("check prescription:")) {
            handleCheckPrescriptionCallBack(chatId, callbackData);
        } else if (callbackData.startsWith("back to main menu")) {
            sendStartMenu(chatId);
        } else if (callbackData.startsWith("back_btn:")) {
            handleBackupCallback(chatId, callbackData);
        } else {
            handleDefaultCallback(chatId, callbackData);
        }
    }

    private void handleBackupCallback(String chatId, String callbackData) {
        //BackupButton processing
        String handleNumber = callbackData.split(":")[1];
        switch (handleNumber) {
            case "0":
                //Show MainMenu
                sendMenu(chatId, GetButtons.getListsStartMenu(), MenuHeader.CHOOSE_ACTION);
                break;
            case "1":
                //Show Specialization
                handleStart1(chatId);
                break;
            case "2":
                //Show list of Doctors
                handleSpecializationCallback(chatId, userVariableMap.get(chatId).getHistoryCallbackData().get(1));
                break;
            case "3":
                //Show list of Dates
                handleDoctorCallback(chatId, userVariableMap.get(chatId).getHistoryCallbackData().get(2));
                break;
            case "4":
                //Show list of Times
                handleDateCallback(chatId, userVariableMap.get(chatId).getHistoryCallbackData().get(3));
                break;
            case "5":
                //Show types of call
                handleTimeCallback(chatId, userVariableMap.get(chatId).getHistoryCallbackData().get(4));
                break;
            case "6":
                handleTypeCallback(chatId, userVariableMap.get(chatId).getHistoryCallbackData().get(5));
        }
    }

    private void saveCallback(String chatId, String callbackData, int number) {
        userVariableMap.get(chatId).setHistoryCallbackData(
                userVariableMap.get(chatId).getHistoryCallbackData() != null
                        ? userVariableMap.get(chatId).getHistoryCallbackData()
                        : new ConcurrentHashMap<>()
        );
        userVariableMap.get(chatId).getHistoryCallbackData().put(number, callbackData);
    }

    private void handleSpecializationCallback(String chatId, String callbackData) {
        String specializationName = callbackData.substring("specialization:".length());
        saveCallback(chatId, callbackData, 1);
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
        saveCallback(chatId, callbackData, 2);
    }

    private void handleDateCallback(String chatId, String callbackData) {
        userVariableMap.get(chatId).setDateSchedule(callbackData.substring("Date:".length()));
        sendMenu(chatId, GetButtons.getListsTimesByDoctorAndDate(userVariableMap.get(chatId).getDoctorId(),
                        userVariableMap.get(chatId).getDateSchedule()),MenuHeader.CHOOSE_TIME);
        saveCallback(chatId, callbackData, 3);
    }

    private void handleTimeCallback(String chatId, String callbackData) {
        saveCallback(chatId, callbackData, 4);
        sendMenu(chatId, GetButtons.getListsScheduleType(), MenuHeader.CHOOSE_APPOINTMENT_TYPE);
        userVariableMap.get(chatId).setTimeSchedule(callbackData.substring("Time:".length()));
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

        saveCallback(chatId, callbackData, 5);
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
            if (userVariableMap.get(chatId).getLastMessageId() != null) {
                deleteMessage(chatId, userVariableMap.get(chatId).getLastMessageId());
            }
            userVariableMap.get(chatId).setLastMessageId(messageId);
        } catch (TelegramApiException ignored) {

        }
    }


    public void sendMsg(String chatId, String text, String type) {
        if (text != null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            message.setParseMode(type);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRegistration(String messageText, String chatId) {
        switch (userVariableMap.get(chatId).getRegistrationStep()) {
            case 0:
                userVariableMap.get(chatId).getUserRegistrationDto().setFirstname(messageText);
                sendMsg(chatId, "Great! Now enter your last name:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
                break;
            case 1:
                userVariableMap.get(chatId).getUserRegistrationDto().setLastname(messageText);
                sendMsg(chatId, "Great! Now enter your email:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
                break;
            case 2:
                userVariableMap.get(chatId).getUserRegistrationDto().setEmail(messageText);
                sendMsg(chatId, "Great! Now enter your phone number:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
                break;
            case 3:
                userVariableMap.get(chatId).getUserRegistrationDto().setPhoneNumber(messageText);
                sendMsg(chatId, "Great! Now enter your address:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
                break;
            case 4:
                userVariableMap.get(chatId).getUserRegistrationDto().setAddress(messageText);
                sendMsg(chatId, "Great! Now enter your city of residence:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
                break;
            case 5:
                userVariableMap.get(chatId).getUserRegistrationDto().setCity(messageText);
                sendMsg(chatId, "Great! Now enter your country of residence:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
                break;
            case 6:
                userVariableMap.get(chatId).getUserRegistrationDto().setCountry(messageText);
                sendMsg(chatId, "Great! Now enter your postal code:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
                break;
            case 7:
                userVariableMap.get(chatId).getUserRegistrationDto().setPostalCode(messageText);
                sendMsg(chatId, "Great! Now enter your insurance number:");
                userVariableMap.get(chatId).setRegistrationStep(userVariableMap.get(chatId).getRegistrationStep() + 1);
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
        userVariableMap.get(chatId).setAddToCart(false);
    }

    private void sendStartMenu(String chatId) {
        sendMenu(chatId, GetButtons.getListsStartMenu(), MenuHeader.CHOOSE_ACTION);
    }


    private void handleStart2(String chatId) {
        if (!registrationUser.isHaveUser(chatId)) {
            userVariableMap.get(chatId).setUserRegistrationDto(new UserRegistrationDto());
            userVariableMap.get(chatId).setRegistrationStep(0);
            startRegistration(chatId);
        }

        String userId = userService.getUserIdByChatId(chatId);
        //проверка на наличие рецептов в базе
        List<PrescriptionDto> prescriptions = prescriptionService.getActivePrescriptions(userId);
        if (!prescriptions.isEmpty()) {
            sendMenu(chatId, GetButtons.getYesNoButtons(
                    "Yes",
                    "Choose medicines",
                    "choose the prescription",
                    "do not stock the prescription"
            ), "You have prescriptions. Would you like to stock your recipes?");
        } else {
            sendMenu(chatId, GetButtons.getMedicineCategoryButtons(), MenuHeader.CHOOSE_MEDICINE_CATEGORY);
        }
    }

    private void handleMedicineCategoryCallback(String chatId, String callbackData) {
        String categoryName = callbackData.substring("category:".length());
        sendMenu(chatId, GetButtons.getListsMedicines(categoryName), MenuHeader.CHOOSE_MEDICINE);
    }

    private void handleMedicineCallback(String chatId, String callbackData) {
        String medicineName = callbackData.substring("medicine:".length());
        MedicineDto medicineDto = medicineService.getByName(medicineName);
        sendMsg(chatId, "Medicine: " + medicineDto.getName() + "\n"
                + "Description: " + medicineDto.getDescription() + "\n"
                + "Price: " + medicineDto.getPrice());
        sendMsg(chatId, "Enter quantity");
        userVariableMap.get(chatId).setAddToCart(true);
        userVariableMap.get(chatId).setMedicineNameForCart(medicineDto);
    }

    private void handleQuantity(String messageText, String chatId, MedicineDto medicineDto) {
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

    private void handleToCartCallBack(String chatId, String callbackData) {
        String userId = userService.getUserIdByChatId(chatId);
        List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);
        if (cartItems.isEmpty()) {
            sendMenu(chatId, GetButtons.getListsStartMenu(), MenuHeader.CART_IS_EMPTY);
        } else {
            List<CartItem> cartWithoutDoubles = getCartWithoutDoubles(cartItems);
            sendMenu(chatId, GetButtons.getCart(userId, chatId, cartWithoutDoubles), MenuHeader.CHOOSE_ITEM_FOR_DELETE);
        }
    }

    private void handleDeleteItemCallBack(String chatId, String callbackData) {
        String medicineId = callbackData.substring("delete item:".length());
        String userId = userService.getUserIdByChatId(chatId);
        cartItemService.deleteAllByMedicineAndUser(medicineId, userId);
        handleToCartCallBack(chatId, callbackData);
    }

    private void handleCheckoutCallBack(String chatId, String callbackData) {
        String userId = userService.getUserIdByChatId(chatId);
        List<CartItem> cart = cartItemService.getCartItemsByUserId(userId);
        List<CartItem> cartWithoutDoubles = getCartWithoutDoubles(cart);
        StringBuilder cartMessage = new StringBuilder();
        final BigDecimal[] sum = {BigDecimal.valueOf(0)};
        cartWithoutDoubles
                .forEach(cartItem -> {
                    BigDecimal price = cartItem.getMedicine().getPrice();
                    BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
                    cartMessage.append(cartItem.getMedicine().getName()).append(", ");
                    cartMessage.append("price: $").append(price).append(", ");
                    cartMessage.append("quantity: ").append(quantity).append("\n");
                    BigDecimal itemTotal = price.multiply(quantity);
                    sum[0] = sum[0].add(itemTotal);
                });
        cartMessage.append("Total sum: ");
        String stringSum = Arrays.toString(sum);
        String result = stringSum.substring(1, stringSum.length() - 1);
        cartMessage.append("$").append(result);
        sendMsg(chatId, String.valueOf(cartMessage));

        sendMenu(chatId, GetButtons.getYesNoButtons(
                "Pickup",
                "Courier delivery",
                "pickup",
                "courier"
        ), MenuHeader.DELIVERY_METHOD);

        userVariableMap.get(chatId).setAddToCart(false);
    }

    private List<CartItem> getCartWithoutDoubles(List<CartItem> list) {
        Map<Medicine, Integer> mergedMap = list.stream()
                .filter(item -> item.getMedicine() != null) // Фильтр для исключения null значений Medicine
                .collect(Collectors.groupingBy(
                        CartItem::getMedicine,
                        Collectors.summingInt(CartItem::getQuantity)
                ));

        return mergedMap.entrySet().stream()
                .map(entry -> {
                    CartItem cartItem1 = new CartItem();
                    cartItem1.setMedicine(entry.getKey());
                    cartItem1.setQuantity(entry.getValue());
                    return cartItem1;
                })
                .toList();
    }

    private void handlePickupCallBack(String chatId) {
        String message = "You can pick up your order at 'Healthy Pharmacy' at 456 Oak Street, 12345, Berlin." +
                " We are waiting for you from 8AM to 9PM every day. Thank you for your order!";
        sendMenu(chatId, GetButtons.getListsStartMenu(), message);
    }

    private void handleCourierCallBack(String chatId) {
        String userId = userService.getUserIdByChatId(chatId);
        UserInfoDto user = userService.getUserById(userId);
        String message = "We will deliver your order to the address: "
                + user.getAddress() + ", "
                + user.getPostalCode() + ", "
                + user.getCity()
                + ". We wish you good health!";
        sendMenu(chatId, GetButtons.getListsStartMenu(), message);
    }

    private void handleChoosePrescriptionCallBack(String chatId) {
        String userId = userService.getUserIdByChatId(chatId);
        List<PrescriptionDto> prescriptions = userService.getUserPrescriptions(UUID.fromString(userId));
        sendMenu(chatId, GetButtons.getListPrescription(userId), MenuHeader.CHOOSE_PRESCRIPTION);
    }

    private void handlePrescriptionCallBack(String chatId, String callbackData) {
        Prescription prescription = prescriptionService.getPrescription(callbackData.substring("prescription:".length()));
        String prescriptionString = prescription.toString();
        sendMsg(chatId, prescriptionString);
        sendMenu(chatId, GetButtons.getYesNoButtons(
                "CHECKOUT",
                "Back to menu",
                "check prescription:" + prescription.getPrescriptionId(),
                "back to main menu"
        ), MenuHeader.CHECKOUT_OR_MENU);
    }

    private void handleCheckPrescriptionCallBack(String chatId, String callbackData) {
        String prescriptionId = callbackData.substring("check prescription:".length());
        PrescriptionDto prescriptionDto = prescriptionService.getPrescriptionDto(prescriptionId);
        String userId = userService.getUserIdByChatId(chatId);
        UserInfoDto user = userService.getUserById(userId);
        prescriptionDto.setDeliveryAddress(user.getAddress());
        OrderFromPrescriptionDto orderFromPrescriptionDto = orderService.addOrder(prescriptionDto);
        sendMsg(chatId, orderFromPrescriptionDto.toString());
        sendMenu(chatId, GetButtons.getYesNoButtons(
                "Pickup",
                "Courier delivery",
                "pickup",
                "courier"
        ), MenuHeader.DELIVERY_METHOD);
    }
}

