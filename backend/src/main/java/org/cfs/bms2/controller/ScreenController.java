package org.cfs.bms2.controller;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.dto.ScreenRequest;
import org.cfs.bms2.entity.Screen;
import org.cfs.bms2.service.ScreenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {
    private final ScreenService screenService;

    @GetMapping("/screen/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long id){
        return ResponseEntity.ok(screenService.getScreenById(id));
    }
    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens(){
        return ResponseEntity.ok(screenService.getAllScreens());
    }
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Screen>> getScreenByTheatreId(@PathVariable Long theaterId){
        return ResponseEntity.ok(screenService.getScreenByTheatreId(theaterId));
    }
    @PostMapping("/admin")
    public ResponseEntity<Screen> addScreen(@RequestBody ScreenRequest screenRequest){
        return ResponseEntity.ok(screenService.addScreen(screenRequest));
    }


}
