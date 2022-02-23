package com.dikann.webservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CategoryDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private @NotBlank String name;
    @JsonProperty(value = "description")
    private String desc;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

}
