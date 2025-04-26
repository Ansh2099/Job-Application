package com.Job.Application.Repo;

import com.Job.Application.Constants.UserConstants;
import com.Job.Application.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    @Query(name = UserConstants.FindByEmail)
    Optional<User> findByEmail(String email);

    @Query(name = UserConstants.FindByUsername)
    Optional<User> findByUsername(String username);

    @Query(name = UserConstants.ExistsByEmail)
    boolean existsByEmail(String email);
} 