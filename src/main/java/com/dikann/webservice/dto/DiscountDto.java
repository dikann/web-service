package com.dikann.webservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class DiscountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private @NotBlank String name;
    @JsonProperty(value = "description")
    private String desc;
    @JsonProperty(value = "discount_percentage")
    @DecimalMax("100.0")
    @DecimalMin("0.0")
    private @NotNull Double discountPercentage;
    private @NotNull Boolean active;
    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY )
    private LocalDateTime modifiedDate;

}
