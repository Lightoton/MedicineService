package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.entity.Order;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GetNameFromUserUtil {
    public static String getName(Order order){
        return order.getUser().getFirstname()+" "+order.getUser().getLastname();
    }
}
