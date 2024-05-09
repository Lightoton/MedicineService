package com.rangers.medicineservice.annotation.user;

import com.rangers.medicineservice.dto.UserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
        summary = "Update user data",
        description = "Update user data",
        requestBody = @RequestBody(
                description = "The user data to be changed.",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserInfoDto.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully returned updated user",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserInfoDto.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Task with this ID was not found.",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        value = "{\n  \"message\": \"!!!! User with this ID was not found.\"\n}"
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "** It is not UUID format **",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        value = "{\n  \"message\": \"** It is not UUID format **\"\n}"
                                )
                        )
                )
        },
        security = {
                @SecurityRequirement(name = "safety requirements")
        }
)
public @interface UpdateUserDocumentation {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
