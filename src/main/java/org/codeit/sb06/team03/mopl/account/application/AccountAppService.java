package org.codeit.sb06.team03.mopl.account.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.*;
import org.codeit.sb06.team03.mopl.account.application.out.*;
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
public class AccountAppService implements RegisterAccountUseCase, AssignRoleUseCase, ResetPasswordUseCase, UpdatePasswordUseCase, AssignRoleUseCase, UpdateLockStatusUseCase {

    private final AccountService accountService;
    private final LoadAccountPort loadAccountPort;
    private final CreateUserPort createUserPort;
    private final SaveAccountPort saveAccountPort;
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
    public void updatePassword(String accountId, UpdatePasswordCommand command) {

        // 불러오기
        final UUID accountUUID = parseUUID(accountId);
        Account account = loadAccountPort.findById(accountUUID)
                .orElseThrow(() -> new AccountNotFoundException(accountUUID));

        // 새 비밀번호로 변경
        accountService.updatePassword(account, command.newPassword());

        // 저장, 임시 비밀번호 삭제
        saveAccountPort.save(account);
        deletePasswordResetPort.deleteByAccountId(accountUUID);
    }

    @Override
    @Transactional
    public void assignRole(UUID userId, AssignRoleCommand command) {
        // 제공받은 프로토 타입은 user-account를 분리하지 않았지만
        // 이벤트 스토밍 과정에서 user와 account가 분리되었고
        // 프론트엔드는 고정되어 있기에 현재 저희 프로젝트에서 userId는 AccountId의 의미로 사용되고 있습니다.
        final UUID accountUuid = userId;

        if (!Role.contains(command.role())) {
            throw new InvalidRoleException(command.role());
        }

        final Role role = Role.valueOf(command.role());

        Account foundAccount = loadAccountPort.findById(accountUuid)
                .orElseThrow(() -> new AccountNotFoundException(accountUuid));

        Account updatedAccount = accountService.updateRole(foundAccount, role);

        saveAccountPort.save(updatedAccount);
    }

    @Override
    @Transactional
    public void updateLocked(UUID userId, UpdateLockStatusCommand command) {
        final UUID accountUuid = userId;
        final boolean locked = command.locked();

        Account foundAccount = loadAccountPort.findById(accountUuid)
                .orElseThrow(() -> new AccountNotFoundException(accountUuid));

        Account updatedAccount = accountService.updateLocked(foundAccount, locked);

        saveAccountPort.save(updatedAccount);
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidIdentifierException(id);
        }
    }
}
