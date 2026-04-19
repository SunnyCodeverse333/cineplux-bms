package org.cfs.bms2.controller;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.dto.TheaterRequest;
import org.cfs.bms2.entity.Theater;
import org.cfs.bms2.service.TheaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;

    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheatres(){
        return ResponseEntity.ok(theaterService.getAllTheatre());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheatreById(@PathVariable Long id){
        return ResponseEntity.ok(theaterService.getTheatreById(id));
    }
    @GetMapping("city/{cityId}")
    public ResponseEntity<List<Theater>> getTheatreByCity(@PathVariable Long cityId){
        return ResponseEntity.ok(theaterService.getTheatreByCity(cityId));
    }
    @PostMapping("/admin")
    public ResponseEntity<Theater> addTheatre(@RequestBody TheaterRequest theaterRequest){
        return ResponseEntity.ok(theaterService.addTheatre(theaterRequest));
    }


}
