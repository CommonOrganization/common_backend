package com.junghun.common.domain.like.service;

import com.junghun.common.domain.like.repository.LikeDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeDailyService {

    private final LikeDailyRepository repository;
}
