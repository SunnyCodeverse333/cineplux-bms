package org.cfs.bms2.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.dto.ScreenRequest;
import org.cfs.bms2.entity.Screen;
import org.cfs.bms2.entity.Theater;
import org.cfs.bms2.repository.ScreenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter
@RequiredArgsConstructor
@Service
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheaterService theaterService;

    //add  screen --Insertion doesn't require Id
    public Screen addScreen(ScreenRequest screenRequest){
       Theater theater = theaterService.getTheatreById(screenRequest.getTheaterId());
        Screen screen = Screen.builder().theater(theater).name(screenRequest.getName()).build();
        return screenRepository.save(screen);
    }
    public List<Screen> getAllScreens(){
        return screenRepository.findAll();
    }
    public Screen getScreenById(Long id){
        return screenRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Screen Id "+id+ " Not found")
        );

    }
    public List<Screen> getScreenByTheatreId(Long id){
        return screenRepository.findByTheater_Id(id);
    }
//    orElseThrow(new Supplier<RuntimeException>() {
//        @Override
//        public RuntimeException get() {
//            return new RuntimeException("error");
//        }
//    });

}
