package com.application.godzilla.repository;

import com.application.godzilla.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Long countByEmail(String email);

    Long countByEmailAndIdNotLike(String email, Long id);

    User findByEmail(String email);

}
