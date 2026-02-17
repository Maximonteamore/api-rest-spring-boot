package com.application.rest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.application.rest.DataProvider;
import com.application.rest.entities.Maker;
import com.application.rest.persistence.IMakerDAO;
import com.application.rest.service.impl.MakerServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MakerServiceTest {

    @Mock
    private IMakerDAO makerDAO;

    @InjectMocks
    private MakerServiceImpl makerService;

    @Test
    public void listarTest() {

        when(makerDAO.listar()).thenReturn(DataProvider.makerListMock());

        List<Maker> result = makerService.listar();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("xiaomi", result.get(0).getName());

        verify(makerDAO).listar();
    }

    @Test
    public void buscarPorIdTest() {

        Long id = 1L;

        when(makerDAO.buscar_por_id(id)).thenReturn(DataProvider.makerFindById());

        Optional<Maker> maker = makerService.buscar_por_id(id);

        assertTrue(maker.isPresent());
        assertEquals("xiaomi", maker.get().getName());

        verify(makerDAO).buscar_por_id(id);
    }

    @Test
    public void guardarTest() {

        Maker maker = Maker.builder()
                .id(3L)
                .name("Asus")
                .build();

        makerService.guardar(maker);

        verify(makerDAO, times(1)).guardar(maker);
        verifyNoMoreInteractions(makerDAO);
    }

    @Test
    public void borrarTest() {

        Long id = 2L;

        makerService.borrar(id);

        verify(makerDAO, times(1)).borrar(id);
        verifyNoMoreInteractions(makerDAO);
    }
}
