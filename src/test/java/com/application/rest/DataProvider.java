package com.application.rest;

import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DataProvider {


   static Maker maker = Maker.builder()
            .name("xiaomi")
            .build();
   static Maker maker1 = Maker.builder()
            .name("lenovo")
            .build();

    public static List<Product> productListMock(){
        System.out.println("lista mock");
        return List.of(
               new Product (1L, "Discoduro SATA3",new BigDecimal("90.99"),maker),
                new Product(2l, "Memoria Ram A3",new BigDecimal("80.99"),maker1),
               new Product(3l, "Discoduro SSD",new BigDecimal("30.99"),maker)
        );
    }

    public static Optional<Product> productFindById(){
        System.out.println("product mock");
        Product result =  new Product(4L, "Discoduro SATA3", new BigDecimal("90.99"),maker);
        return Optional.of(result);

    }

    //maker
    public static List<Maker> makerListMock(){
        return List.of(
                Maker.builder().id(1L).name("xiaomi").build(),
                Maker.builder().id(2L).name("lenovo").build()
        );
    }

    public static Optional<Maker> makerFindById(){
        return Optional.of(
                Maker.builder().id(1L).name("xiaomi").build()
        );
    }


}
