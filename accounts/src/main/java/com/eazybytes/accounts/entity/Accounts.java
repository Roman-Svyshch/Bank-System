package com.eazybytes.accounts.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Accounts extends BaseEntity {

    private Long customerId;
    @Id
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
