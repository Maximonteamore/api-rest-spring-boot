package com.application.rest.controllers;


import com.application.rest.controllers.dto.ProductDTO;
import com.application.rest.entities.Product;
import com.application.rest.service.IMakerService;
import com.application.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> buscar_id(@PathVariable Long id){
        Optional<Product> productOptional = productService.buscar_por_id(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();

            return ResponseEntity.ok(productDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> buscar_todo(){
        List<ProductDTO> productList = productService.listar()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                ).toList();
                return ResponseEntity.ok(productList);
    }
    @PostMapping("/save")
    public  ResponseEntity<?> guardar(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if(productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null){
            return ResponseEntity.badRequest().build();
        }

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build();

        productService.guardar(product);

        return ResponseEntity.created(new URI("/api/product/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody ProductDTO productDTO){

        Optional<Product> productOptional = productService.buscar_por_id(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setMaker(productDTO.getMaker());
            productService.guardar(product);
            return ResponseEntity.ok("registro actualizado");
        }

        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> borrar_id(@PathVariable Long id){

        if(id !=null){
            productService.borrar(id);
            return ResponseEntity.ok("registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

}


