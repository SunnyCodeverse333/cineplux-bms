package org.cfs.bms2.controller;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.dto.SeatRequest;
import org.cfs.bms2.entity.Seat;
import org.cfs.bms2.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Seat>> getSeatByScreenId(@PathVariable Long screenId){
        return ResponseEntity.ok(seatService.getSeatByScreenId(screenId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id){
        return ResponseEntity.ok(seatService.getSeatById(id));
    }
    @PostMapping("/admin")
    public ResponseEntity<Seat> addSeat(@RequestBody SeatRequest seatRequest){
        return ResponseEntity.ok(seatService.addSeat(seatRequest));
    }



}
