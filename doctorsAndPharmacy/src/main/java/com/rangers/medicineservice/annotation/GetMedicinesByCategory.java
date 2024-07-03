package com.rangers.medicineservice.annotation;

import com.rangers.medicineservice.controller.handler.ErrorExtension;
import com.rangers.medicineservice.entity.Medicine;
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
        summary = "Show all medicines by category",
        description = "Get a list of medicines by category",
        tags = {"MEDICINE"},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "All medicines found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = Medicine.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Possible answers:" +
                                "<br> - No such category" +
                                "<br> - There are no medicines",
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
public @interface GetMedicinesByCategory {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
