package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "ResponseDTO",
        description = "Schema to hold Customer and Account Information"
)
public class ResponseDto {

    @Schema(
            name = "StatusCode",
            description = "Status code in the response",
            example = "200"
    )
private String statusCode;

    @Schema(
            name = "Status Message",
            description = "Status Message in the response ",
            example = "Request processed Successfully"
    )
private String statusMsg;
}
