package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.dto.CreatedCartItemDto;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/prescriptions/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Get user prescriptions", description = "Get list of user prescriptions by userId")
    @ApiResponse(
            responseCode = "200",
            description = "***Prescriptions returned successfully***",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatedCartItemDto.class))
            })
    public List<Prescription> getUserPrescription(@PathVariable UUID id) {
        return userService.getUserPrescriptions(id);
    }
}
