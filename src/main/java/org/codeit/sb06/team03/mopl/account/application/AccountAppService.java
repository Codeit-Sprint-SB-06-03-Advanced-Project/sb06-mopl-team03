package org.codeit.sb06.team03.mopl.account.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountCommand;
import org.codeit.sb06.team03.mopl.account.application.in.RegisterAccountUseCase;
import org.codeit.sb06.team03.mopl.account.application.in.UpdatePasswordCommand;
import org.codeit.sb06.team03.mopl.account.application.in.UpdatePasswordUseCase;
import org.codeit.sb06.team03.mopl.account.application.out.CreateUserPort;
import org.codeit.sb06.team03.mopl.account.application.out.LoadAccountPort;
import org.codeit.sb06.team03.mopl.account.application.out.SaveAccountPort;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.AccountService;
import org.codeit.sb06.team03.mopl.account.domain.entity.PasswordReset;
import org.codeit.sb06.team03.mopl.account.domain.exception.AccountRegistrationFailedException;
import org.codeit.sb06.team03.mopl.account.domain.exception.EmailAddressAlreadyExistsException;
import org.codeit.sb06.team03.mopl.account.domain.policy.PasswordEncryptionPolicy;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.account.domain.vo.Password;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountAppService implements RegisterAccountUseCase, UpdatePasswordUseCase {

    private final AccountService accountService;
    private final LoadAccountPort loadAccountPort;
    private final CreateUserPort createUserPort;
    private final SaveAccountPort saveAccountPort;
    private final PasswordEncryptionPolicy passwordEncryptionPolicy;

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
    public void updatePassword(String accountId, UpdatePasswordCommand command) {

        // TODO AccountService로 분리

        // 불러오기
        UUID accountUUID = parseUUID(accountId);
        Account account = loadAccountPort.findByAccountId(accountUUID)
                .orElseThrow(() -> new RuntimeException("")); // TODO 커스텀예외

        PasswordReset passwordReset = loadPasswordResetPort.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException(""));

        // 임시 비밀번호 검증
        Password encryptedInput = passwordEncryptionPolicy.apply(command.tempPassword());

        if (!passwordReset.validateTempPassword(encryptedInput)) {
            throw new RuntimeException(); // TODO 커스텀예외
        }

        // 새 비밀번호로 변경
        Password password = passwordEncryptionPolicy.apply(command.newPassword());
        accountService.updatePassword(password);
        saveAccountPort.save(account);

        // 임시 비밀번호 삭제
        deletePasswordResetPort.delete(passwordReset); // 임시 비밀번호 삭제
    }
}
