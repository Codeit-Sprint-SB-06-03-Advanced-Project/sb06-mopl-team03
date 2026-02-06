package org.codeit.sb06.team03.mopl.account.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountCommand;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountUseCase;
import org.codeit.sb06.team03.mopl.account.application.in.UpdatePasswordCommand;
import org.codeit.sb06.team03.mopl.account.application.in.UpdatePasswordUseCase;
import org.codeit.sb06.team03.mopl.account.application.out.*;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.AccountService;
import org.codeit.sb06.team03.mopl.account.domain.PasswordResetService;
import org.codeit.sb06.team03.mopl.account.domain.entity.PasswordReset;
import org.codeit.sb06.team03.mopl.account.domain.exception.*;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class AccountAppService implements RegisterAccountUseCase, UpdatePasswordUseCase {

    private final AccountService accountService;
    private final PasswordResetService passwordResetService;
    private final LoadAccountPort loadAccountPort;
    private final CreateUserPort createUserPort;
    private final SaveAccountPort saveAccountPort;
    private final LoadPasswordResetPort loadPasswordResetPort;
    private final DeletePasswordResetPort deletePasswordResetPort;

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
        createUserPort.create(name)
                .exceptionally(throwable -> {
                    throw new AccountRegistrationFailedException(throwable);
                })
                .join();

        saveAccountPort.save(newAccount);
        return newAccount;
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordCommand command) {

        // 불러오기
        final UUID accountUUID = parseUUID(command.accountId());
        Account account = loadAccountPort.findByAccountId(accountUUID)
                .orElseThrow(() -> new AccountNotFoundException(accountUUID));
        PasswordReset passwordReset = loadPasswordResetPort.findByAccountId(accountUUID)
                .orElseThrow(() -> new PasswordResetNotFound(accountUUID));

        // 임시 비밀번호 검증
        passwordResetService.validateTempPassword(passwordReset, command.tempPassword());

        // 새 비밀번호로 변경
        accountService.updatePassword(account, command.newPassword());

        // 저장, 임시 비밀번호 삭제
        saveAccountPort.save(account);
        deletePasswordResetPort.deleteByAccountId(accountUUID);
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdentifierException(id);
        }
    }
}
