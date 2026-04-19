package org.cfs.bms2.repository;


import org.cfs.bms2.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long> {

    List<Screen> findByTheater_Id(Long id);
    
}
