package com.dikann.webservice.dto;

import com.dikann.webservice.views.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonView(value = {Views.Default.class})
public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private @NotBlank String name;
    @JsonProperty(value = "description")
    private String desc;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    private String sku;
    @DecimalMin("0.0")
    private @NotNull Double price;
    @Min(0)
    private Long quantity;
    @JsonView(value = {Views.Detailed.class})
    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonView(value = {Views.Detailed.class})
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

    @JsonProperty(value = "discount_id", access = JsonProperty.Access.WRITE_ONLY)
    private Long discountId;
    @JsonProperty(value = "category_id", access = JsonProperty.Access.WRITE_ONLY)
    private @NotNull Long categoryId;

    @JsonProperty(value = "discount", access = JsonProperty.Access.READ_ONLY)
    private DiscountDto discountDto;
    @JsonProperty(value = "category", access = JsonProperty.Access.READ_ONLY)
    private CategoryDto categoryDto;

}
