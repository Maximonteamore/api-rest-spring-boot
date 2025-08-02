package com.application.rest.controllers;


import com.application.rest.controllers.dto.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maker")

//recibo y retorno dto y no entidades por seguridad de los datos.

public class MakerController {

    @Autowired
    private IMakerService iMakerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> buscar_id(@PathVariable Long id){
        Optional<Maker> makerOptional = iMakerService.buscar_por_id(id);

        if(makerOptional.isPresent()){
            Maker maker = makerOptional.get();
         //creo un dto con los valores de la entidad y retorno el dto y no una entindad
            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .productList(maker.getProductList())
                    .build();

            return  ResponseEntity.ok(makerDTO);
        }
            return  ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> buscar_todo(){
        List<MakerDTO> makerList = iMakerService.listar()
                .stream()//convierto la entidad en un dto  lo retorno
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .productList(maker.getProductList())
                        .build())
                .toList();
        return ResponseEntity.ok(makerList);
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {

        //si esta vacio retorno
        if(makerDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
    //Si no esta vacio llamo al metodo guardar pero le paso una entidad y que construyo en con builder
        //le pasos los valores del parametro dto por que la funcion utiliza una entidad y no un dto
        iMakerService.guardar(Maker.builder()
                .name(makerDTO.getName())
                .build());
        return ResponseEntity.created(new URI("/api/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO){
        Optional<Maker> makerOptional = iMakerService.buscar_por_id(id);

        //controlo si existe entonces cambio los datos que recibo,lo guardo y retorno
        if(makerOptional.isPresent()){
            Maker  maker = makerOptional.get();//creo una entidad con la entidad que encontro el metodo imakerserice
            maker.setName(makerDTO.getName());
            iMakerService.guardar(maker);
            return  ResponseEntity.ok("registro acutalizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> borrar_id(@PathVariable Long id){
        if(id != null){
            iMakerService.buscar_por_id(id);
            return  ResponseEntity.ok("registro eliminad");
        }
        return ResponseEntity.badRequest().build();
    }

}
