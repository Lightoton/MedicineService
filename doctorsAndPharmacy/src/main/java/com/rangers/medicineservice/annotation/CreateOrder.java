package com.rangers.medicineservice.annotation;

import com.rangers.medicineservice.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.POST)
@Operation(
        summary = "Create a new order",
        description = "Creation of a new order and return",
        tags = {"ORDER"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "The order to be created",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Order.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "The order created",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Order.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "The order already exist",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Order.class)
                        )
                )
        }
)
public @interface CreateOrder {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
