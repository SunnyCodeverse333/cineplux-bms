package org.cfs.bms2.controller;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.dto.MovieRequest;
import org.cfs.bms2.entity.Movie;
import org.cfs.bms2.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> getMovieByTitle(@RequestParam String title){
        return ResponseEntity.ok(movieService.SearchByTitle(title));
    }
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre){
        return ResponseEntity.ok(movieService.SearchByGenre(genre));
    }
    @GetMapping("/language/{lang}")
    public ResponseEntity<List<Movie>> getMovieByLanguage(@PathVariable String lang){
        return ResponseEntity.ok(movieService.SearchByLanguage(lang));
    }

    @PostMapping("/admin")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieRequest movieRequest){
        Movie movie =Movie.builder().title(movieRequest.getTitle()).
                genre(movieRequest.getGenre()).language(movieRequest.getLanguage()).
                durationMinutes(movieRequest.getDurationMinutes()).
                rating(movieRequest.getRating()).releaseDate(movieRequest.getReleaseDate()).build();
        return ResponseEntity.ok(movieService.AddMovie(movie));
    }
}
