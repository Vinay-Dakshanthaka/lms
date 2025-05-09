package com.totfd.lms.repository;

import com.totfd.lms.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByVerificationToken(String token);

    @Query("SELECT u FROM Users u JOIN FETCH u.role WHERE u.email = :email")
    Optional<Users> findByEmailWithRole(@Param("email") String email);

}
