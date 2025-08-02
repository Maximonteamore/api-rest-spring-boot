package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private  String name;

    @Column(name = "precio")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_fabricante",nullable = false)
    private Maker maker;

}
