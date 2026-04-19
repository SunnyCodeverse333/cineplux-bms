package org.cfs.bms2.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequest {
    private String title;
    private String genre;
    private String language;
    private Integer durationMinutes;
    private Double rating;
    private LocalDate releaseDate;
}
