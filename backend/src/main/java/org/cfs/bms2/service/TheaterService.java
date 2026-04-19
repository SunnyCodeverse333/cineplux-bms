package org.cfs.bms2.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.dto.TheaterRequest;
import org.cfs.bms2.entity.City;
import org.cfs.bms2.entity.Theater;
import org.cfs.bms2.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter @Setter
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final CityService cityService;


    public Theater addTheatre(TheaterRequest request){
        City city = cityService.getCityById(request.getCityId());


        Theater theater =  Theater.builder().name(request.getName()).
                address(request.getAddress()).
                city(city).
                build();
        return theaterRepository.save(theater);

    }
    public List<Theater> getAllTheatre(){
        return theaterRepository.findAll();
    }
    public Theater getTheatreById(Long id){
        return theaterRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Theatre Not found with id "+id
                )
        );
    }
    public List<Theater> getTheatreByCity(Long cityId){
        return theaterRepository.findByCity_Id(cityId);
    }



}
