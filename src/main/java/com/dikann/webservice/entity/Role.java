package com.dikann.webservice.entity;

import com.dikann.webservice.enums.RoleName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private RoleName name;

}
