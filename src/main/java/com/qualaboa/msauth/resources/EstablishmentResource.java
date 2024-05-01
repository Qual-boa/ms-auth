package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dto.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dto.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.entities.enums.CategoryTypeEnum;
import com.qualaboa.msauth.entities.enums.DrinkEnum;
import com.qualaboa.msauth.entities.enums.FoodEnum;
import com.qualaboa.msauth.entities.enums.MusicEnum;
import com.qualaboa.msauth.services.EstablishmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/establishments")
public class EstablishmentResource {

    @Autowired
    private EstablishmentService service;

    @PostMapping
    public ResponseEntity<EstablishmentResponseDTO> save(@RequestBody @Valid EstablishmentCreateDTO dto){
        EstablishmentResponseDTO responseDTO = service.save(dto);
        return ResponseEntity.created(URI.create("/establishments")).body(responseDTO);
    }

    @GetMapping("/listbyfilters")
    public ResponseEntity<List<EstablishmentResponseDTO>> getListByfilters(
            @RequestParam List<CategoryTypeEnum> categories,
            @RequestParam List<MusicEnum> musics,
            @RequestParam List<FoodEnum> foods,
            @RequestParam List<DrinkEnum> drinks){
        List<EstablishmentResponseDTO> responseDTO = service.findListByFilters(categories, musics, foods, drinks);
        if(responseDTO == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(responseDTO);
    }
}
