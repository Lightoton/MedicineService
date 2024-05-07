package com.rangers.medicineservice.annotation.user;

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
@RequestMapping(method = RequestMethod.GET)
@Operation(
        summary = "Find user by chatId",
        description = "Getting user by chatId",
        parameters = {
                @Parameter(
                        name = "id",
                        description = "The unique chatId identifier of the user",
                        required = true,
                        in = ParameterIn.PATH,
                        schema = @Schema(type = "string", format = "string")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully returned userId",
                        content = @Content(
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "User with this ID was not found.",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        value = "{\n  \"message\": \"!!!! User with this ID was not found.\"\n}"
                                )
                        )
                )
        },
        security = {
                @SecurityRequirement(name = "safety requirements")
        }
)
public @interface GetUserIdByChatIdDocumentation {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
