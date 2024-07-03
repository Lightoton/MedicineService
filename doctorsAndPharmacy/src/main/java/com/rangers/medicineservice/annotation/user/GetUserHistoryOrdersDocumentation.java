package com.rangers.medicineservice.annotation.user;

import com.rangers.medicineservice.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
        summary = "Show history of user orders.",
        description = "Show history of user orders.",
        parameters = {
                @Parameter(
                        name = "id",
                        description = "The unique UUID identifier of the user",
                        required = true,
                        in = ParameterIn.PATH,
                        schema = @Schema(type = "string", format = "uuid")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully returned user orders.",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Order.class))
                        )
                )
        },
        security = {
                @SecurityRequirement(name = "safety requirements")
        }
)
public @interface GetUserHistoryOrdersDocumentation {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
