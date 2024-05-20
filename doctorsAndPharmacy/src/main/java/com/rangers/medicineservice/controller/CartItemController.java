package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.GetCart;
import com.rangers.medicineservice.dto.CartItemBeforeCreationDto;
import com.rangers.medicineservice.dto.CreatedCartItemDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.service.interf.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create CartItem", description = "Creating CartItem by CreateCartItemDto")
    @ApiResponse(
            responseCode = "200",
            description = "***CartItem created successfully***",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatedCartItemDto.class))
            })
    public CreatedCartItemDto createCartItem(@RequestBody CartItemBeforeCreationDto cartItemBeforeCreationDto) {
        return cartItemService.createCartItem(cartItemBeforeCreationDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete CartItem", description = "Deleting CartItem from db")
    @ApiResponse(
            responseCode = "200",
            description = "***CartItem deleted successfully***",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatedCartItemDto.class))
            })
    public ResponseEntity<String> deleteCartItem(@PathVariable(name = "id") UUID id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("***Deleted successfully!***");
    }

    @GetCart(path = "/getCart/{userId}")
    public List<CartItem> getCartItemsByUserId(@PathVariable(name = "userId") String id){
        List<CartItem> list = cartItemService.getCartItemsByUserId(id);
        System.out.println("222222222222222222" +list);
        return list;
    }
}
