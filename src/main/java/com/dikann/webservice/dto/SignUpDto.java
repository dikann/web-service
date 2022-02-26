package com.dikann.webservice.dto;

import com.dikann.webservice.enums.DefaultRoles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public class SignUpDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private @Email @NotBlank String email;
    private @NotBlank String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private @NotBlank String password;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "family_name")
    private String familyName;
    @JsonProperty(value = "created_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(value = "modified_date", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedDate;
    private List<String> roles = Arrays.asList(DefaultRoles.USER.toString());

}
