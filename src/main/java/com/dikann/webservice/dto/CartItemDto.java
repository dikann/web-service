package com.dikann.webservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CartItemDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(value = "product_id", access = JsonProperty.Access.READ_WRITE)
    private @NotNull Long productId;
    @Min(1)
    private @NotNull Long quantity;

    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

    @JsonProperty(value = "product", access = JsonProperty.Access.READ_ONLY)
    private ProductDto productDto;

}
