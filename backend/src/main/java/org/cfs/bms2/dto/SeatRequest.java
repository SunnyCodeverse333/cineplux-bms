package org.cfs.bms2.dto;

import lombok.*;
import org.cfs.bms2.enums.SeatType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequest {

    private String seatNumber;
    private String row;
    private Integer col;
    private SeatType seatType;
    private Long screenId;

}
//{
//  "seatNumber": "A1",
//  "row": "A",
//  "col": 1,
//  "seatType": "PREMIUM",
//  "screenId": 7
//}