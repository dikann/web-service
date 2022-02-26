package com.dikann.webservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAddressDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(value = "address_line_one")
    private String addressLineOne;
    @JsonProperty(value = "address_line_two")
    private String addressLineTwo;
    @JsonProperty(value = "postal_code")
    private String postalCode;
    private String city;
    private String country;
    private String phone;

}
