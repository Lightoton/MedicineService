package com.rangers.medicineservice.annotation;

import com.rangers.medicineservice.controller.handler.ErrorExtension;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Prescription;
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
        summary = "Show prescription by prescriptionId",
        description = "Get a prescription by prescriptionId",
        tags = {"PRESCRIPTION"},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "The prescription received",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Prescription.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not found",
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
public @interface GetPrescription {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
