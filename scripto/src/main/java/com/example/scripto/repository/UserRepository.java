package com.example.scripto.repository;

import com.example.scripto.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM user as u WHERE u.email = :email")
//    void deleteUserByEmail(@Param("email") String email);
    void deleteByEmail(String email);

//    User findUserById(Long buyerId);
}
