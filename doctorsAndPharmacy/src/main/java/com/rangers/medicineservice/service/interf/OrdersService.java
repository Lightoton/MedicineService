package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.PrescriptionDto;

public interface OrdersService {
    OrderDto addOrder(PrescriptionDto prescriptionDto);
}
