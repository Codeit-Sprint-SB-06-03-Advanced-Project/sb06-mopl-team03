package org.codeit.sb06.team03.mopl.account.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.AssignRoleCommand;
import org.codeit.sb06.team03.mopl.account.application.in.AssignRoleUseCase;
import org.codeit.sb06.team03.mopl.account.application.in.*;
import org.codeit.sb06.team03.mopl.account.application.out.CreateUserPort;
import org.codeit.sb06.team03.mopl.account.application.out.LoadAccountPort;
import org.codeit.sb06.team03.mopl.account.application.out.SaveAccountPort;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.AccountService;
import org.codeit.sb06.team03.mopl.account.domain.Role;
import org.codeit.sb06.team03.mopl.account.domain.exception.*;
import org.codeit.sb06.team03.mopl.account.domain.exception.AccountRegistrationFailedException;
import org.codeit.sb06.team03.mopl.account.domain.exception.EmailAddressAlreadyExistsException;
import org.codeit.sb06.team03.mopl.account.domain.exception.EmailAddressNotFoundException;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountAppService implements RegisterAccountUseCase, ResetPasswordUseCase, AssignRoleUseCase {

    private final AccountService accountService;
    private final LoadAccountPort loadAccountPort;
    private final CreateUserPort createUserPort;
    private final SaveAccountPort saveAccountPort;

    @Override
    @Transactional
    public Account register(RegisterAccountCommand command) {
        final String name = command.name();
        final EmailAddress emailAddress = command.emailAddress();
        final String rawPassword = command.rawPassword();

        if (loadAccountPort.existsByEmailAddress(emailAddress)) {
            throw new EmailAddressAlreadyExistsException(emailAddress.value());
        }
        Account newAccount = accountService.create(emailAddress, rawPassword);
        createUserPort.create(newAccount.getId(), name)
                .exceptionally(throwable -> {
                    throw new AccountRegistrationFailedException(throwable);
                })
                .join();

        saveAccountPort.save(newAccount);
        return newAccount;
    }

    @Override
    @Transactional
    public Account resetPassword(ResetPasswordCommand command) {
        final EmailAddress emailAddress = command.emailAddress();

        if (!loadAccountPort.existsByEmailAddress(emailAddress)) {
            throw new EmailAddressNotFoundException(emailAddress);
        }
        Account existAccount = loadAccountPort.findByEmailAddress(emailAddress);

        Account resetPasswordAccount = accountService.resetPassword(existAccount);

        saveAccountPort.save(resetPasswordAccount);
        return resetPasswordAccount;
    }

    @Override
    @Transactional
    public void assignRole(String userId, AssignRoleCommand command) {
        // 제공받은 프로토 타입은 user-account를 분리하지 않았지만
        // 이벤트 스토밍 과정에서 user와 account가 분리되었고
        // 프론트엔드는 고정되어 있기에 현재 저희 프로젝트에서 userId는 AccountId의 의미로 사용되고 있습니다.
        String accountId = userId;
        UUID accountUuid = parseUUID(accountId);

        if (!Role.contains(command.role())) {
            throw new InvalidRoleException(command.role());
        }

        Role role = Role.valueOf(command.role());

        Account foundAccount = loadAccountPort.findById(accountUuid)
                .orElseThrow(() -> new AccountNotFoundException(accountUuid));

        Account updatedAccount = accountService.updateRole(foundAccount, role);

        saveAccountPort.save(updatedAccount);
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountIdFormatException(id);
        }
    }
}
