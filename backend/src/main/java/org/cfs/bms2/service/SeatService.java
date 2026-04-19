package org.cfs.bms2.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cfs.bms2.dto.SeatRequest;
import org.cfs.bms2.entity.Screen;
import org.cfs.bms2.entity.Seat;
import org.cfs.bms2.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter @Setter
@RequiredArgsConstructor

public class SeatService {
    private final ScreenService screenService;
    private final SeatRepository seatRepository;


    public Seat addSeat(SeatRequest seatRequest){
        Screen screen = screenService.getScreenById(seatRequest.getScreenId());
        Seat seat = Seat.builder().seatNumber(seatRequest.getSeatNumber()).
                row(seatRequest.getRow()).col(seatRequest.getCol()).
                seatType(seatRequest.getSeatType()).screen(screen).
                build();
        return seatRepository.save(seat);
    }
    public List<Seat> getAllSeats(){
        return seatRepository.findAll();
    }
    public List<Seat> getSeatByScreenId(Long id){
        return seatRepository.findByScreen_Id(id);
    }
    public Seat getSeatById(Long id){
        return seatRepository.findById(id).orElseThrow(
                ()-> new RuntimeException(" seat not found with id "+id)
        );
    }


}
