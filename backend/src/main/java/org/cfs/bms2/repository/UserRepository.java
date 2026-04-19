package org.cfs.bms2.repository;


import org.cfs.bms2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


//    custom methods
//    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
//    boolean existsByEmail(String email);

//    save(user);          // insert/update
//    findById(id);       // get by id
//    findAll();          // get all users
//    deleteById(id);     // delete
}
