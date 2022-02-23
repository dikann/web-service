package com.dikann.webservice.dto;

import com.dikann.webservice.views.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonView(value = {Views.Default.class})
public class DiscountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private @NotBlank String name;
    @JsonView(value = {Views.Detailed.class})
    @JsonProperty(value = "description")
    private String desc;
    @JsonProperty(value = "discount_percentage")
    @DecimalMax("100.0")
    @DecimalMin("0.0")
    private @NotNull Double discountPercentage;
    private @NotNull Boolean active;
    @JsonView(value = {Views.Detailed.class})
    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonView(value = {Views.Detailed.class})
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

}
