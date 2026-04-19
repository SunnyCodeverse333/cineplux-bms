package org.cfs.bms2.controller;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.entity.City;
import org.cfs.bms2.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities(){
        return ResponseEntity.ok(cityService.getAllCities());
    }
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id){
        return ResponseEntity.ok(cityService.getCityById(id));
    }
    @PostMapping("/admin")
    public ResponseEntity<City> addCity(@RequestBody City city){
        return ResponseEntity.ok(cityService.addCity(city));
    }


}
