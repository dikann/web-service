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
    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

    @JsonView(value = {Views.Post.class})
    @JsonProperty(value = "discount_id")
    private @NotNull Long discountId;
    @JsonView(value = {Views.Post.class})
    @JsonProperty(value = "category_id")
    private @NotNull Long categoryId;

    @JsonView(value = {Views.Get.class})
    @JsonProperty(value = "discount")
    private DiscountDto discountDto;
    @JsonView(value = {Views.Get.class})
    @JsonProperty(value = "category")
    private CategoryDto categoryDto;

}
