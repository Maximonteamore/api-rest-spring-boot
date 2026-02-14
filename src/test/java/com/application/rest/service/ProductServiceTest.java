package com.application.rest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.application.rest.DataProvider;
import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.persistence.impl.IProductDAOImpl;
import com.application.rest.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)//habilito la anotaciones de mockito
public class ProductServiceTest {

    @Mock
    private IProductDAOImpl productRepository;

    @InjectMocks
    private ProductServiceImpl produtService;


    @Test
    public void listarTest(){
        //given

        //when
        when(productRepository.listar()).thenReturn(DataProvider.productListMock());
        List<Product> result =  produtService.listar();

        //then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Discoduro SATA3",result.get(0).getName());
        assertEquals("xiaomi",result.get(0).getMaker().getName());
        verify(this.productRepository).listar();
    }

    @Test
    public void FindById(){
        Long id = 4L;
        BigDecimal num = BigDecimal.valueOf(90.99);

        when(productRepository.buscar_por_id(anyLong())).thenReturn(DataProvider.productFindById());
        Optional<Product> product = this.produtService.buscar_por_id(id);

        assertNotNull(product);
        assertEquals("Discoduro SATA3",product.get().getName());
        assertEquals(num,product.get().getPrice());
        assertEquals("xiaomi",product.get().getMaker().getName());
        verify(productRepository).buscar_por_id(anyLong());

    }

    @Test
    public void listar_rango_preciosTest(){
        BigDecimal num1 = BigDecimal.valueOf(80.99);
        BigDecimal num2 = BigDecimal.valueOf(90.99);
          Maker maker = Maker.builder()
                .name("xiaomi")
                .build();
        Maker maker1 = Maker.builder()
                .name("lenovo")
                .build();

        List<Product> pro = List.of(new Product (1L, "Discoduro SATA3",new BigDecimal("90.99"),maker),
                new Product(2L, "Memoria Ram A3",new BigDecimal("80.99"),maker1));

        when(productRepository.listar_rango_precios(num1,num2)).thenReturn(pro);
        List<Product> list = produtService.listar_rango_precios(num1,num2);

        assertNotNull(list);
        assertEquals(pro,list);
       verify(productRepository).listar_rango_precios(any(),any());

    }

    @Test
    public void guardarTest(){
        Product product = Product.builder()
                .id(5L)
                .name("memoria")
                .price(BigDecimal.valueOf(100.24))
                        .build();

        produtService.guardar(product);

        verify(productRepository, times(1)).guardar(product);
        verifyNoMoreInteractions(productRepository);

    }

    @Test
    public void borrarTest(){
        Long id = 4L;

        produtService.borrar(id);

        verify(productRepository,times(1)).borrar(id);
        verifyNoMoreInteractions(productRepository);

    }

}
