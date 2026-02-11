package org.codeit.sb06.team03.mopl.common.security;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.exception.AccountNotFoundException;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.account.infra.out.AccountRepository;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.domain.vo.TimeoutImage;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoplUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmailAddress emailAddress = new EmailAddress(email);
        Account account = accountRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new AccountNotFoundException(emailAddress));

        UserDto userDto = getUserDto(account);

        return new MoplUserDetails(userDto, account.getPassword().value());
    }

    private static UserDto getUserDto(Account account) {
        UUID accountId = account.getId();

        Profile profile = account.getProfile();
        TimeoutImage timeoutImage = profile.getTimeoutImage();
        String profileImageUrl = timeoutImage != null ? timeoutImage.getPresignedUrl() : null;

        UserDto userDto = new UserDto(
                accountId,
                account.getCreatedAt(),
                account.getEmailAddress().value(),
                profile.getName(),
                profileImageUrl,
                account.getRole().name(),
                account.isLocked()
        );
        return userDto;
    }
}
