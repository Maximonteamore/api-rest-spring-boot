package com.application.rest.controllers;


import com.application.rest.controllers.dto.ProductDTO;
import com.application.rest.entities.Product;
import com.application.rest.service.IMakerService;
import com.application.rest.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@Tag(
        name = "Products",
        description = "Endpoints for managing products"
)
@SecurityRequirement(name = "Security Token")

public class ProductController {

    @Autowired
    private IProductService productService;

    //FIND PRODUCT BY ID
    @GetMapping("/find/{id}")
    @Operation(
            summary = "Find product by ID",
            description = "Returns a product based on its unique identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content
                    )
            }
    )

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

    //FIND ALL PRODUCTS
    @GetMapping("/findAll")
    @Operation(
            summary = "Get all products",
            description = "Returns a list of all registered products",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of products retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class)
                            )
                    )
            }
    )
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

    //SAVE PRODUCT
    @PostMapping("/save")
    @Operation(
            summary = "Save new product",
            description = "Creates a new product in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product data to be saved",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid product data",
                            content = @Content
                    )
            }
    )
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

    //UPDATE PRODUCT
    @PutMapping("/update/{id}")
    @Operation(
            summary = "Update product",
            description = "Updates an existing product identified by its ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content
                    )
            }
    )
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


    //DELETE PRODUCT
    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Delete product by ID",
            description = "Deletes a product from the system using its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid ID supplied",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<?> borrar_id(@PathVariable Long id){

        if(id !=null){
            productService.borrar(id);
            return ResponseEntity.ok("registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

}


