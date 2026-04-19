package org.cfs.bms2.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowRequest {

    private Long movieId;
    private Long screenId;

    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Double ticketPrice;
}