package com.restapi.template.account;

import com.restapi.template.account.dto.UserIdDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserIdAndState(String userId, UserStatus state);

    Optional<UserIdDto> findByUserIdAndStateIsNot(String userId, UserStatus state);
}
