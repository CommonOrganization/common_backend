package com.junghun.common.domain.user.service;

import com.junghun.common.domain.user.dto.UserCategoryDto;
import com.junghun.common.domain.user.entity.UserCategory;
import com.junghun.common.domain.user.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryRepository repository;

    public void upload(UserCategoryDto userCategoryDto) {
        for(String category : userCategoryDto.getCategoryList()){
            UserCategory userCategory = UserCategory.builder()
                    .user(userCategoryDto.getUser())
                    .category(category)
                    .build();

            repository.save(userCategory);
        }
    }

    public void deleteAll(Long userId) {
        List<UserCategory> userCategoryList = repository.findByUserId(userId);
        for(UserCategory userCategory : userCategoryList){
            repository.deleteById(userCategory.getId());
        }
    }

}
