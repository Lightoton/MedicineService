package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.service.interf.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Order", description = "Creating Order by CartItem and OrderDetail")
    @ApiResponse(
            responseCode = "200",
            description = "***Order created successfully***",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatedOrderDto.class))
            })
    public CreatedOrderDto createOrder(@RequestBody Set<CartItem> cartItems) {
        return service.createOrder(cartItems);
    }
}
