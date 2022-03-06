package com.dikann.webservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String status;
    @JsonProperty(value = "total_discount")
    private Double totalDiscount;

    @JsonProperty(value = "user_address_id", access = JsonProperty.Access.WRITE_ONLY)
    private Long userAddressId;
    @JsonProperty(value = "user_address", access = JsonProperty.Access.READ_ONLY)
    private UserAddressDto userAddressDto;

    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

}
