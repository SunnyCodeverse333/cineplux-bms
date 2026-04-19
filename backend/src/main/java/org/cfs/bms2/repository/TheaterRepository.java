package org.cfs.bms2.repository;

import org.cfs.bms2.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TheaterRepository extends JpaRepository<Theater,Long> {


    List<Theater> findByCity_Id(Long cityId);
}
