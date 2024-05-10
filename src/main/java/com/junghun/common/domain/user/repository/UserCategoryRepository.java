package com.junghun.common.domain.user.repository;

import com.junghun.common.domain.user.entity.UserCategory;
import com.junghun.common.domain.user.entity.UserPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findByUserId(Long userId);
}