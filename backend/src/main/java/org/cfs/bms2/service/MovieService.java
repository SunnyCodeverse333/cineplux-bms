package org.cfs.bms2.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.entity.Movie;
import org.cfs.bms2.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter @Getter
@RequiredArgsConstructor

public class MovieService {

    private final MovieRepository movieRepository;

    public Movie AddMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }
    public Movie getMovieById(Long id){
        return movieRepository.findById(id).
                orElseThrow(()->new RuntimeException("Movie id " + id +"Not found ")
                        );
    }
    public List<Movie> SearchByTitle(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
    public List<Movie> SearchByGenre(String gen){
        return movieRepository.findByGenre(gen);
    }
    public List<Movie> SearchByLanguage(String lang){
        return movieRepository.findByLanguage(lang);
    }
    public Movie updateMovie(Long id , Movie movie){
        Movie existingMovie  = movieRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Invalid Movie Id "+ id));

        existingMovie.setTitle(movie.getTitle());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setLanguage(movie.getLanguage());
        existingMovie.setRating(movie.getRating());
        existingMovie.setDurationMinutes(movie.getDurationMinutes());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setPosterUrl(movie.getPosterUrl());

        return movieRepository.save(existingMovie);
    }
    public void deleteMovie(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));

        movieRepository.delete(movie);
    }


}
