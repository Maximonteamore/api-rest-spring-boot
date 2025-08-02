package com.application.rest.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fabricante")
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private  String name;

    //relacion 1 a muchos product mapeado con el atributo maker,movimiento en cascada,fetch no sbrecargar listado,orphanremoval habilita si elimino un creador se elimina el producto automaticamente.
    @OneToMany(mappedBy = "maker",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    private  List<Product> productList = new ArrayList<>();
}
