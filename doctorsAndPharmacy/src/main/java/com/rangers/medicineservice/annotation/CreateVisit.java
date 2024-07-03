package com.rangers.medicineservice.annotation;

import com.rangers.medicineservice.controller.handler.ErrorExtension;
import com.rangers.medicineservice.dto.CancelVisitRequestDto;
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
        summary = "Create a schedule entry",
        description = "Create an appointment with the selected doctor at the selected time. The controller accepts" +
                " schedule_id in path variable, user_id and type of appointment in request body, returns " +
                "date and time, doctor fullName, user fullName, link (if the visit will be online) or address " +
                "(if the visit will be offline). In addition, if the appointment is successful, " +
                "a message is sent to the user's mailbox",
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
                description = "The user_id to be paste on the schedule and appointment type",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CreateVisitRequestDto.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Response to be created",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CreateVisitResponseDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "User does not exist or schedule does not exist",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorExtension.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "There is no doctor on the schedule or there is already a patient",
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
public @interface CreateVisit {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
