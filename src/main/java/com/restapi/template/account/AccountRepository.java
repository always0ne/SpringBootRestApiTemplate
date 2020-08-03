package com.restapi.template.account;

import com.restapi.template.account.dto.UserIdDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 계정 레포지터리
 *
 * @author always0ne
 * @version 1.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * 계정 ID와 상태로 조회
     *
     * @param userId 사용자 ID
     * @param state  조회할 상태
     * @return 계정(Optional)
     */
    Optional<Account> findByUserIdAndState(String userId, UserStatus state);

    /**
     * 계정 ID와 제외된 상태로 조회
     *
     * @param userId 사용자 ID
     * @param state  제외할 상태
     * @return 계정(Optional)
     */
    Optional<UserIdDto> findByUserIdAndStateIsNot(String userId, UserStatus state);

    /**
     * 계정 ID와 상태로 조회
     *
     * @param userId       사용자 ID
     * @param state        조회할 상태
     * @param refreshToken RefreshToken
     * @return 계정(Optional)
     */
    Optional<Account> findByUserIdAndStateAndRefreshToken(String userId, UserStatus state, String refreshToken);
}
