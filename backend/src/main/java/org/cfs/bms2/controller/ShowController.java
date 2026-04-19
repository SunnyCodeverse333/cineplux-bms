package org.cfs.bms2.controller;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.dto.ShowRequest;
import org.cfs.bms2.entity.Show;
import org.cfs.bms2.service.ShowService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id){
        return ResponseEntity.ok(showService.getShowById(id));
    }
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowByMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(showService.getShowByMovieId(movieId));
    }
//    URL params are like a map (key-value), not a list — so order never matters
//    In GET methods, we use @RequestParam for query data and @PathVariable for URL path data — both come from the URL.
    @GetMapping("/movie/{movieId}/date")
    public ResponseEntity<List<Show>> getShowByMovieAndDate(@PathVariable Long movieId ,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok(showService.getShowByMovieAndDate(movieId , date));
    }

    @PostMapping("/admin")
    public ResponseEntity<Show> addShow(@RequestBody ShowRequest showRequest){
        return ResponseEntity.ok(showService.addShow(showRequest));
    }





}
