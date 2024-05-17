package com.rangers.medicineservice.entity;


import com.rangers.medicineservice.model.ChatMassage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    private int index;
    private ChatMassage message;
}
