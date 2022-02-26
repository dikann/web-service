package com.dikann.webservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "address_line_one")
    private String addressLineOne;
    @Column(name = "address_line_two")
    private String addressLineTwo;
    @Column(name = "postal_code")
    private String postalCode;
    private String city;
    private String country;
    private String phone;
    @Column(name = "user_id")
    private Long userId;

}
