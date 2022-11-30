package com.dev.health.service;

import com.dev.health.dto.ForUpdateHealthStatusResDto;
import com.dev.health.dto.UpdateMemberHealthReq;
import com.dev.health.dto.UpdateMemberHealthRes;
import com.dev.health.entity.HealthStatus;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.member.entity.Member;
import com.dev.member.repository.MemberRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberHealthService {

    private final HealthStatusRepository healthStatusRepository;

    @Transactional
    public UpdateMemberHealthRes UpdateMemberHealthInfo(UpdateMemberHealthReq updateMemberHealthReq){
        Long memberId = SecurityUtil.getCurrentMemberId();
        Optional<HealthStatus> findHealthStatus = healthStatusRepository.findByMemberAndDate(memberId, updateMemberHealthReq.getDate());

        if (findHealthStatus.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }
        HealthStatus healthStatus = findHealthStatus.get();
        healthStatus.setInfo(updateMemberHealthReq.getHeight(),updateMemberHealthReq.getWeight(),updateMemberHealthReq.getGender(),updateMemberHealthReq.getActivity(),updateMemberHealthReq.getTarget());

        healthStatus.setNeedCalorie();
        healthStatus.setNeedNutrients();
        healthStatusRepository.save(healthStatus);

        return UpdateMemberHealthRes.builder()
                .date(updateMemberHealthReq.getDate())
                .build();
    }

    @Transactional
    public ForUpdateHealthStatusResDto getForUpdateHealthStatus(LocalDate date){
        Long userId = SecurityUtil.getCurrentMemberId();
        Optional<HealthStatus> findHealthStatus = healthStatusRepository.findByMemberAndDate(userId, date);
        if (findHealthStatus.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }
        HealthStatus healthStatus = findHealthStatus.get();
        return ForUpdateHealthStatusResDto.builder()
                .height(healthStatus.getHeight())
                .weight(healthStatus.getWeight())
                .gender(healthStatus.getGender())
                .activity(healthStatus.getActivity())
                .target(healthStatus.getTarget())
                .date(healthStatus.getDate())
                .build();
    }
}
