package com.dev.recommend.service;

import com.dev.health.repository.NutrientStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendService {

    private final NutrientStatusRepository nutrientStatusRepository;

}
