package org.cfs.bms2.repository;


import org.cfs.bms2.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SeatRepository extends JpaRepository<Seat , Long> {

    List<Seat> findByScreen_Id(Long screen_Id);

}
