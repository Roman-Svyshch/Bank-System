package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response",
        description = "Schema to hold Errors information"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            name = "apiPath",
            description = "API path invoked by client"
    )
    private String apiPath;

    @Schema(
            name = "error Code",
            description = "API Code represent the error"
    )
    private HttpStatus errorCode;

    @Schema(
            name = "Error Message",
            description = "API error message represent what happened"
    )
    private String errorMessage;

    @Schema(
            name = "Error Time",
            description = "Error time represent when error happened"
    )
    private LocalDateTime errorTime;
}
