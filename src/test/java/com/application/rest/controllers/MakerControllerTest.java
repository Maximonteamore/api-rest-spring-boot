package com.application.rest.controllers;

import com.application.rest.entities.Maker;
import com.application.rest.service.IMakerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MakerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MakerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean     //Crea un mock dentro del contexto de Spring.
    private IMakerService makerService;

    @Autowired
    private ObjectMapper objectMapper;


    // GET BY ID - OK

    @Test
    void buscarPorId_OK() throws Exception {

        Maker maker = Maker.builder()
                .id(1L)
                .name("Xiaomi")
                .productList(List.of())
                .build();

        when(makerService.buscar_por_id(1L))
                .thenReturn(Optional.of(maker));

        mockMvc.perform(get("/api/maker/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Xiaomi"));
    }

    // GET BY ID - NOT FOUND

    @Test
    void buscarPorId_NotFound() throws Exception {

        when(makerService.buscar_por_id(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/maker/find/1"))
                .andExpect(status().isNotFound());
    }

    // GET ALL

    @Test
    void buscarTodoTest() throws Exception {

        Maker maker = Maker.builder()
                .id(1L)
                .name("Samsung")
                .productList(List.of())
                .build();

        when(makerService.listar())
                .thenReturn(List.of(maker));

        mockMvc.perform(get("/api/maker/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Samsung"));
    }


    // SAVE OK

    @Test
    void save_OK() throws Exception {

        String json = """
                {
                    "name": "Apple"
                }
                """;

        doNothing().when(makerService).guardar(org.mockito.ArgumentMatchers.any(Maker.class));

        mockMvc.perform(post("/api/maker/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    // SAVE BAD REQUEST

    @Test
    void save_BadRequest() throws Exception {

        String json = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(post("/api/maker/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }


    // UPDATE OK

    @Test
    void update_OK() throws Exception {

        Maker maker = Maker.builder()
                .id(1L)
                .name("OldName")
                .productList(List.of())
                .build();

        when(makerService.buscar_por_id(1L))
                .thenReturn(Optional.of(maker));

        String json = """
                {
                    "name": "NewName"
                }
                """;

        mockMvc.perform(put("/api/maker/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    // UPDATE NOT FOUND

    @Test
    void update_NotFound() throws Exception {

        when(makerService.buscar_por_id(1L))
                .thenReturn(Optional.empty());

        String json = """
                {
                    "name": "NewName"
                }
                """;

        mockMvc.perform(put("/api/maker/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    // DELETE OK

    @Test
    void delete_OK() throws Exception {

        doNothing().when(makerService).borrar(1L);

        mockMvc.perform(delete("/api/maker/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("registro eliminad"));
    }
}
