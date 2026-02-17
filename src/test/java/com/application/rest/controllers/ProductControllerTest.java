package com.application.rest.controllers;


import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;

    @Test
    void buscarPorId() throws Exception {

        Product product = Product.builder()
                .id(1L)
                .name("Discoduro SSD")
                .price(BigDecimal.valueOf(25.2))
                .build();

        when(productService.buscar_por_id(1L))
                .thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/product/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Discoduro SSD"));
    }

    @Test
    void buscarPorId_NotFound() throws Exception {

        when(productService.buscar_por_id(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/product/find/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    void buscarTODO() throws Exception{

        Product product = Product.builder()
                .id(1L)
                .name("Discoduro SSD")
                .price(BigDecimal.valueOf(25.2))
                .build();

        when(productService.listar())
                .thenReturn(List.of(product));

        mockMvc.perform(get("/api/product/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Discoduro SSD"));

    }

    @Test
    void save_OK() throws Exception {

               String json = """
        {
            "name": "Apple",
                "price": 1200.0,
                "maker": {
            "id": 1,
                    "name": "Samsung"
        }
        }
        """;
        
                doNothing().when(productService).guardar(org.mockito.ArgumentMatchers.any(Product.class));
        
                mockMvc.perform(post("/api/product/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isCreated());
        
            }
        
            @Test
            void save_BadRequest() throws Exception {
        
                String json = """
                {
                    "name": "Apple"
                }
                """;
        doNothing().when(productService).guardar(org.mockito.ArgumentMatchers.any(Product.class));

        mockMvc.perform(post("/api/product/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    void update_OK() throws Exception {

        Product product = Product.builder()
                .id(1L)
                .name("Discoduro SSD")
                .price(BigDecimal.valueOf(25.2))
                .build();

        when(productService.buscar_por_id(1L))
                .thenReturn(Optional.of(product));

        String json = """
        {
            "name": "Apple",
                "price": 1200.0,
                "maker": {
            "id": 1,
                    "name": "Samsung"
        }
        }
        """;

        mockMvc.perform(put("/api/product/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void update_NotFound() throws Exception {

        when(productService.buscar_por_id(1L))
                .thenReturn(Optional.empty());

        String json = """
                {
                    "name": "NewName"
                }
                """;

        mockMvc.perform(put("/api/product/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_OK() throws Exception {

        doNothing().when(productService).borrar(1L);

        mockMvc.perform(delete("/api/product/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("registro eliminado"));
    }

}
