package org.cfs.bms2.repository;


import org.cfs.bms2.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findByGenre(String genre);
    List<Movie> findByLanguage(String lang);
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
