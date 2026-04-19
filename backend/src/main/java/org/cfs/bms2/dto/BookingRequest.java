package org.cfs.bms2.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    private Long showId;
    private List<Long> seatIds;
}
