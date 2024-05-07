package com.rangers.medicineservice.annotation;

import com.rangers.medicineservice.controller.handler.ErrorExtension;
import com.rangers.medicineservice.dto.CancelVisitRequestDto;
import com.rangers.medicineservice.dto.CancelVisitResponseDto;
import com.rangers.medicineservice.dto.CreateVisitRequestDto;
import com.rangers.medicineservice.dto.CreateVisitResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@RequestMapping(method = RequestMethod.PUT)
@Operation(
        summary = "Cancel a schedule entry",
        description = "Cancel your doctor's visit. The controller takes the schedule_id in the path variable " +
                "and the user_id in the request body. In the database schedule table, the user_id field changes " +
                "to null. The output gives the date and time, the name of the doctor and the name of the user.",
        tags = {"SCHEDULE_DTO"},
        parameters = {
                @Parameter(
                        name = "schedule_id",
                        description = "Selected date and time in the schedule",
                        required = true,
                        in = ParameterIn.PATH,
                        schema = @Schema(format = "uuid")
                )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "The user_id to be canceled",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CancelVisitRequestDto.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Response to be created",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CancelVisitResponseDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "User does not exist",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorExtension.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "There is no appointment on the schedule with this patient",
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
public @interface CancelVisit {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
