package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(
        name = "REST APIs for Customers EazyBank",
        description = " CRUD REST API to fetch Customer details"
)

@RequestMapping(value = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

   private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Fetch Customer details REST API",
            description = "REST API to fetch  Customer details inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Internal Server ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("eazybank-correlation-id")String correlationId,@RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits.Like '0996681573'")
                                                                   String mobileNumber){
        logger.debug("eazybank correlation id found {}",correlationId);

        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetailsDto(mobileNumber,correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
