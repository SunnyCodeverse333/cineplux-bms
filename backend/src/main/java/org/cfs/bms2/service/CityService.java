package org.cfs.bms2.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.entity.City;
import org.cfs.bms2.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@Service
public class CityService {
        private final CityRepository cityRepository;

        public City addCity(City city){
            return cityRepository.save(city);
        }
        public City getCityById(Long id){
            return cityRepository.findById(id).orElseThrow(()-> new RuntimeException("city not found with id "+id));
        }
        public List<City> getAllCities(){
            return cityRepository.findAll();
        }


}
