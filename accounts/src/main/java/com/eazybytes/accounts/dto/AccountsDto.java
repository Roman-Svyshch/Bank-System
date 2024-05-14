package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Schema(
        name = "AccountsDTO",
        description = "Schema to hold Customer and Account Information"
)
public class AccountsDto {

    @Schema(
            description = "Account  of the EazyBank Account",example = "1234567890"
    )
    @NotEmpty(message = "Account Number Can`t be Empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of Eazy Bank"
    )
    @NotEmpty(message = "Account type can`t be empty")
    private String accountType;

    @Schema(
            description = "Branch Address of the account",example = "Bohuna 9"
    )
    @NotEmpty(message = "Address cannot be null or empty")
    private String branchAddress;
}

