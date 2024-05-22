package com.rangers.medicineservice.utils.userVariable;

import com.rangers.medicineservice.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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


}
