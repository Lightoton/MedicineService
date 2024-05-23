package com.rangers.medicineservice.utils.userVariable;

import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
/**
 * User variable storage class
 *
 * @author Viktor
 */
@Getter
@Setter
public class UserVariable {
    private UserRegistrationDto userRegistrationDto;
    private Integer registrationStep;
    private Boolean isRegistrationInProgress;
    private String doctorId;
    private String dateSchedule;
    private String timeSchedule;
    private Integer lastMessageId;
    private String userId;
    private Boolean isSupportInProgress;
    private String scheduleIdForCancel;
    private Boolean addToCart;
    private MedicineDto medicineNameForCart;
    private Boolean isChatInProgress;
    private Map<Integer, String> historyCallbackData;


}
