package com.dikann.webservice.dto;

import com.dikann.webservice.views.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@JsonView(value = {Views.Default.class})
public class CategoryDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private @NotBlank String name;
    @JsonProperty(value = "description")
    private String desc;
    @JsonProperty(value = "image_url")
    private String imageUrl;
    @JsonView(value = {Views.Detailed.class})
    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonView(value = {Views.Detailed.class})
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;

}
