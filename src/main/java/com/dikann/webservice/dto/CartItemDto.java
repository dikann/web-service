package com.dikann.webservice.dto;

import com.dikann.webservice.views.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonView(value = {Views.Default.class})
public class CartItemDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(value = "product_id", access = JsonProperty.Access.WRITE_ONLY)
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
