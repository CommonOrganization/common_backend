package com.junghun.common.domain.user.repository;

import com.junghun.common.domain.user.entity.UserPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPlaceRepository extends JpaRepository<UserPlace, Long> {
    Optional<UserPlace> findByUserId(Long userId);
}