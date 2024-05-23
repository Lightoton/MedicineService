package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.enums.MedicineCategory;
import com.rangers.medicineservice.entity.enums.Specialization;
import com.rangers.medicineservice.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Provides utility methods to generate  buttons for various actions in the chat interface.
 *
 * @author Viktor
 * @author Volha
 * @author Oleksandr
 */
@Service
public class GetButtons {
    public static DoctorServiceImpl service;
    public static ScheduleServiceImpl scheduleService;

    public static MedicineServiceImpl medicineService;
    public static CartItemServiceImpl cartItemService;
    public static UserServiceImpl userService;
    public static PrescriptionServiceImpl prescriptionService;

    @Autowired
    public void setMedicineService(MedicineServiceImpl medicineService) {
        GetButtons.medicineService = medicineService;
    }

    @Autowired
    public void setCartItemService(CartItemServiceImpl cartItemService) {
        GetButtons.cartItemService = cartItemService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        GetButtons.userService = userService;
    }

    public GetButtons(DoctorServiceImpl service, ScheduleServiceImpl scheduleService, MedicineServiceImpl medicineService, PrescriptionServiceImpl prescriptionService) {
        GetButtons.service = service;
        GetButtons.scheduleService = scheduleService;
        GetButtons.medicineService = medicineService;
        GetButtons.prescriptionService = prescriptionService;
    }

    /**
     * Returns a list of buttons for the main menu.
     * @author Viktor
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getListsStartMenu() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Make an appointment with a doctor");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Pharmacy");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Virtual assistant");
        button1.setCallbackData("start1");
        button2.setCallbackData("start2");
        button3.setCallbackData("start3");
        rowInline1.add(button1);
        rowInline.add(button2);
        rowInline.add(button3);
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline);
        return rowsInline;
    }

    /**
     * Returns a list of buttons for scheduling appointments.
     * @author Viktor
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getListsSchedule() {
        List<String> specializations = Arrays.stream(Specialization.values()).map(Enum::toString).toList();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String specialization : specializations) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Make an appointment with " + specialization);
            button.setCallbackData("specialization:" + specialization); // Указываем специализацию в callbackData
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        //add BackupBtn which points to the previous step
        rowsInline.add(getBackupBtn(1));
        return rowsInline;
    }

    /**
     * Returns a list of buttons for selecting doctors by specialization.
     * @author Viktor
     * @param specializations The specialization for which doctors are selected.
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getListsDoctors(String specializations) {
        List<DoctorDto> doctorDtos = service.getDoctors(specializations);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (DoctorDto dto : doctorDtos) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Doctor: " + dto.getFullName()
                    + " Rating: " + dto.getRating());
            button.setCallbackData("Doctor:" + dto.getUuid());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        //add BackupBtn which points to the previous step
        rowsInline.add(getBackupBtn(2));
        return rowsInline;
    }
    /**
     * Generates a list of buttons for selecting appointment dates by doctor.
     * @author Viktor
     * @param doctorId The ID of the doctor.
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getListsDatesByDoctor(String doctorId) {
        List<ScheduleDateTimeDto> date = scheduleService.getScheduleDate(UUID.fromString(doctorId));
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ScheduleDateTimeDto dto : date) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Date: " + dto.getDateAndTime().toLocalDate().toString());
            button.setCallbackData("Date:" + dto.getDateAndTime().toLocalDate());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        rowsInline.add(getBackupBtn(3));
        return rowsInline;
    }
    /**
     * Generates a list of buttons for selecting appointment times by doctor and date.
     * @author Viktor
     * @param doctorId The ID of the doctor.
     * @param date     The selected date for the appointment.
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getListsTimesByDoctorAndDate(String doctorId, String date) {
        List<ScheduleDateTimeDto> time = scheduleService.getScheduleTime(UUID.fromString(doctorId), date);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ScheduleDateTimeDto dto : time) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Time: " + dto.getDateAndTime().toLocalTime());
            button.setCallbackData("Time:" + dto.getDateAndTime().toLocalTime());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        rowsInline.add(getBackupBtn(4));
        return rowsInline;
    }
    /**
     * Generates a list of buttons for selecting the appointment type (online or offline).
     * @author Viktor
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getListsScheduleType() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("ONLINE");
        InlineKeyboardButton button2 = new InlineKeyboardButton("OFFLINE");
        button1.setCallbackData("type:" + button1.getText());
        button2.setCallbackData("type:" + button2.getText());
        rowInline.add(button1);
        rowInline.add(button2);
        rowsInline.add(rowInline);
        rowsInline.add(getBackupBtn(5));
        return rowsInline;
    }
    /**
     * Generates a keyboard markup with a single button.
     * @author Viktor
     * @param text        The text displayed on the button.
     * @param isLocation  Indicates whether the button should request the user's location.
     * @return Keyboard markup with a single button.
     */
    public static ReplyKeyboardMarkup getKeyboardMarkup(String text, boolean isLocation) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton(text);
        button.setRequestLocation(isLocation);
        row.add(button);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        return keyboardMarkup;
    }
    /**
     * Generates a backup button for navigating to the previous step.
     * @author Oleksandr
     * @param number The step number to which the button navigates.
     * @return A list containing the backup button.
     */
    public static List<InlineKeyboardButton> getBackupBtn(int number) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton("<< Back");
        button.setCallbackData("back_btn:" + (number - 1));
        rowInline.add(button);
        return rowInline;
    }
    /**
     * Generates a list of buttons for selecting yes or no options.
     * @author Volha
     * @param nameYes    The label for the 'yes' button.
     * @param nameNo     The label for the 'no' button.
     * @param callBackYes The callback data for the 'yes' button.
     * @param callBackNo  The callback data for the 'no' button.
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getYesNoButtons(String nameYes, String nameNo, String callBackYes, String callBackNo) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton(nameYes);
        InlineKeyboardButton button2 = new InlineKeyboardButton(nameNo);
        button1.setCallbackData(callBackYes);
        button2.setCallbackData(callBackNo);
        rowInline.add(button1);
        rowInline.add(button2);
        rowsInline.add(rowInline);
        return rowsInline;
    }
    /**
     * Generates a list of buttons for selecting medicine categories.
     * @author Volha
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getMedicineCategoryButtons() {
        List<String> categories = Arrays.stream(MedicineCategory.values()).map(Enum::toString).toList();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String category : categories) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(category);
            button.setCallbackData("category:" + category);
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        return rowsInline;
    }
    /**
     * Generates a list of buttons for displaying medicines in a specific category.
     * @author Volha
     * @param category The category of medicines to display.
     * @return A list of button rows.
     */
    public static List<List<InlineKeyboardButton>> getListsMedicines(String category) {
        List<MedicineDto> medicineList = medicineService.getByCategory(category);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (MedicineDto medicine : medicineList) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(medicine.getName() + " price: " +
                    medicine.getPrice());
            button.setCallbackData("medicine:" + medicine.getName());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        return rowsInline;
    }
    /**
     * Generates a list of buttons representing the user's shopping cart.
     * @author Volha
     * @param userId  The ID of the user.
     * @param chatId  The ID of the chat.
     * @param cart    The list of items in the shopping cart.
     * @return A list of button rows representing the shopping cart.
     */
    public static List<List<InlineKeyboardButton>> getCart(String userId, String chatId, List<CartItem> cart) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (CartItem cartItem : cart) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(
                    cartItem.getMedicine().getName()
                            + ". price: $" + cartItem.getMedicine().getPrice()
                            + ", quantity: " + cartItem.getQuantity());
            button.setCallbackData("delete item:" + cartItem.getMedicine().getMedicineId());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton buttonCheckout = new InlineKeyboardButton("CHECKOUT");
        buttonCheckout.setCallbackData("checkout");
        rowInline.add(buttonCheckout);
        rowsInline.add(rowInline);
        return rowsInline;
    }
    /**
     * Generates a list of buttons representing prescriptions for a user.
     * @author Volha
     * @param userId The ID of the user.
     * @return A list of button rows representing prescriptions.
     */
    public static List<List<InlineKeyboardButton>> getListPrescription(String userId) {
        List<PrescriptionDto> prescriptionDtoList = prescriptionService.getActivePrescriptions(userId);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (PrescriptionDto prescription : prescriptionDtoList) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(prescription.toString());
            button.setCallbackData("prescription:" + prescription.getPrescriptionId());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        return rowsInline;
    }
    /**
     * Generates a list of buttons representing active schedules for a user.
     * @author Viktor
     * @param uuid The UUID of the user.
     * @return A list of button rows representing active schedules.
     */
    public static List<List<InlineKeyboardButton>> getListsSchedulesActiveByUser(String uuid) {
        List<ScheduleFullDto> schedules = scheduleService.getSchedulesByUserInProgress(UUID.fromString(uuid));
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ScheduleFullDto dto : schedules) {
            if (dto != null) {
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                InlineKeyboardButton scheduleButton = new InlineKeyboardButton("Date and time: " + dto.getDateAndTime()
                        + ", " + dto.getDoctorSpecialization());
                scheduleButton.setCallbackData("ScheduleUser:" + dto.getScheduleId());
                rowInline.add(scheduleButton);
                rowsInline.add(rowInline);
            }
        }
        return rowsInline;
    }
    /**
     * Generates a list of buttons for cancelling a schedule.
     * @author Viktor
     * @param scheduleId The ID of the schedule to cancel.
     * @return A list of button rows for cancelling the schedule.
     */
    public static List<List<InlineKeyboardButton>> getCancelButtonForSchedule(String scheduleId) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton cancelButton = new InlineKeyboardButton("Cancel");
        cancelButton.setCallbackData("CancelSchedule:" + scheduleId);
        rowInline.add(cancelButton);

        rowsInline.add(rowInline);

        return rowsInline;
    }
    /**
     * Generates a list of buttons for approving or denying the cancellation of a schedule.
     * @author Viktor
     * @return A list of button rows for approving or denying cancellation.
     */
    public static List<List<InlineKeyboardButton>> getApproveCancelButtonForSchedule() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton yes = new InlineKeyboardButton("yes");
        InlineKeyboardButton no = new InlineKeyboardButton("no");
        yes.setCallbackData("ApproveCancel:" + yes.getText());
        no.setCallbackData("ApproveCancel:" + no.getText());
        rowInline.add(yes);
        rowInline.add(no);

        rowsInline.add(rowInline);

        return rowsInline;
    }
}
