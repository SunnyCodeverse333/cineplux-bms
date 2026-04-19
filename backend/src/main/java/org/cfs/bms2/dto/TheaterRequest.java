package org.cfs.bms2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterRequest {
    private String name;
    private String address;
    private Long cityId;

}
