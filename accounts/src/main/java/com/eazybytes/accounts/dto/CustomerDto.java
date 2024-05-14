package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account Information"
)
public class CustomerDto {


    @Schema(
            description = "Name of the Customer",example = "Eazy Bates"
    )
    @NotEmpty(message = "Name can`t be empty")
    @Size(min = 3,max = 30)
    private String name;

    @Schema(
            description = "Email address of the customer",example = "romansvyshch@gmail.com"
    )
    @NotEmpty(message = "email can`t be empty")
    @Email(message = "email address should be a valid name")
    private String email;

    @Schema(
            description = "mobile number of the customer",example = "0996681573"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits.Like '0996681573'")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
