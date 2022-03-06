package com.dikann.webservice.entity;

import com.dikann.webservice.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "total_discount")
    private Double totalDiscount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_session_id", nullable = false)
    private ShoppingSession shoppingSession;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_address_id", nullable = false)
    private UserAddress userAddress;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

}
