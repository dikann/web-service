package com.dikann.webservice.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    public String name;
    @Column(name = "description ")
    public String desc;
    @Column(name = "discount_percentage")
    public Double discountPercentage;
    public boolean active;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

}
