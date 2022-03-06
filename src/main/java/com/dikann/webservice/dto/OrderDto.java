package com.dikann.webservice.dto;

import com.dikann.webservice.entity.CartItem;
import com.dikann.webservice.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private OrderStatus status = OrderStatus.PENDING;
    @JsonProperty(value = "total_discount", access = JsonProperty.Access.READ_ONLY)
    private Double totalDiscount;

    @JsonProperty(value = "user_address_id", access = JsonProperty.Access.WRITE_ONLY)
    private Long userAddressId;
    @JsonProperty(value = "user_address", access = JsonProperty.Access.READ_ONLY)
    private UserAddressDto userAddressDto;

    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

    @JsonProperty(value = "cart_items", access = JsonProperty.Access.READ_ONLY)
    private List<CartItem> cartItems;

}
