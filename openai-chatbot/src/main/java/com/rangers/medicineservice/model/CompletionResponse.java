package com.rangers.medicineservice.model;


import com.rangers.medicineservice.entity.Choice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletionResponse {
    private List<Choice> choices;
}
