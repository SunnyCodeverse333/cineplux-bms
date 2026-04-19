package org.cfs.bms2.repository;


import org.cfs.bms2.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovie_Id(Long movieId);

    List<Show> findByScreen_Id(Long screenId);

    List<Show> findByMovie_IdAndShowDate(Long movieId, LocalDate showDate);

}
