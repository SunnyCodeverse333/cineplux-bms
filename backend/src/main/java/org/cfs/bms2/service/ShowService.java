package org.cfs.bms2.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.dto.ShowRequest;
import org.cfs.bms2.entity.Movie;
import org.cfs.bms2.entity.Screen;
import org.cfs.bms2.entity.Show;
import org.cfs.bms2.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class ShowService {
    private final ScreenService screenService;
    private final MovieService movieService;
    private final ShowRepository showRepository;

    //add
    public Show addShow(ShowRequest showRequest){
        Movie movie =movieService.getMovieById(showRequest.getMovieId());
        Screen screen = screenService.getScreenById(showRequest.getScreenId());
        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .showDate(showRequest.getShowDate())
                .startTime(showRequest.getStartTime())
                .endTime(showRequest.getEndTime())
                .ticketPrice(showRequest.getTicketPrice())
                .build();
        return showRepository.save(show);

    }
    public List<Show> getAllShows(){
        return showRepository.findAll();
    }
    public Show getShowById(Long id){
        return showRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("No show found with given Id "+ id)
        );
    }
    public List<Show> getShowByMovieId(Long movieId){
        return showRepository.findByMovie_Id(movieId);
    }
    public List<Show> getShowByScreenId(Long screenId){
        return showRepository.findByScreen_Id(screenId);
    }
    public List<Show> getShowByMovieAndDate(Long movieId , LocalDate showDate){
        return showRepository.findByMovie_IdAndShowDate(movieId , showDate);
    }



}
