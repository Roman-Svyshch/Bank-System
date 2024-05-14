package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Accounts EazyBank",
        description = " CRUD REST API"
)

@RestController
@RequestMapping(value = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated

public class AccountsController {

  private final IAccountsService accountsService;

  @Autowired
  private AccountsContactDto accountsContactDto;

  @Value("${build.version}")
  private String buildVersion;

    @Autowired
  private Environment environment;



    public AccountsController(IAccountsService accountsService) {
        this.accountsService = accountsService;
    }


    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account REST API",
            description = "REST API to fetch  Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountsDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits.Like '0996681573'")
            String mobileNumber) {
    CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update Account REST API",
            description = "REST API to Update  Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Updated"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomerDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdate = accountsService.updateAccounts(customerDto);
        if (isUpdate){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(
            summary = "DELETE Account REST API",
            description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountsDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits.Like '0996681573'")
            String mobileNumber){
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }



    @Operation(
            summary = "Get build info",
            description = "REST API to fetch  Build Info"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String>getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }


    @Operation(
            summary = "Get java info",
            description = "REST API to fetch  java Info in accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String>getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }




    @Operation(
            summary = "Get contact info",
            description = "REST API to fetch  contact Info in accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactDto>getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactDto);
    }
}
