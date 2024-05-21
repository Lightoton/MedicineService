package com.rangers.medicineservice.annotation;

import com.rangers.medicineservice.controller.handler.ErrorExtension;
import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.entity.CartItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Show cart by userId",
        description = "Get a list of cart items by user id",
        tags = {"CART"},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "All cartItems received",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CartItem.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "No cartItems found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorExtension.class)
                        )
                )
        },
        security = {
                @SecurityRequirement(name = "safety requirements")
        }
)
public @interface GetCart {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
