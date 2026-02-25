package org.codeit.sb06.team03.mopl.bff;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.*;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.common.ContentMapper;
import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.common.UserSummary;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.content.Content;
import org.codeit.sb06.team03.mopl.content.application.in.GetContentUseCase;
import org.codeit.sb06.team03.mopl.user.application.in.UpdateProfileCommand;
import org.codeit.sb06.team03.mopl.user.application.in.UpdateProfileUseCase;
import org.codeit.sb06.team03.mopl.user.domain.Profile;
import org.codeit.sb06.team03.mopl.user.infra.ProfileMapper;
import org.codeit.sb06.team03.mopl.user.infra.in.*;
import org.codeit.sb06.team03.mopl.watchingSession.application.in.GetWatchingSessionUseCase;
import org.codeit.sb06.team03.mopl.watchingSession.domain.WatchingSession;
import org.codeit.sb06.team03.mopl.watchingSession.domain.exception.WatchingSessionNotFoundException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicBffUserService implements BffUserService {

    private final AccountMapper accountMapper;
    private final ProfileMapper profileMapper;
    private final RegisterAccountUseCase registerAccountUseCase;
    private final AssignRoleUseCase assignRoleUseCase;
    private final UpdateLockStatusUseCase updateLockStatusUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final GetWatchingSessionUseCase getWatchingSessionUseCase;
    private final ContentMapper contentMapper;
    private final GetContentUseCase getContentUseCase;

    @Override
    public UserDto registerAccount(UserCreateRequest request) {
        RegisterAccountCommand command = accountMapper.toCommand(request);
        Account newAccount = registerAccountUseCase.register(command);

        return getAccountUseCase.get(newAccount.getId().toString());
    }

    @Override
    public void updatePassword(String userId, PasswordUpdateRequest request) {
        UpdatePasswordCommand command = accountMapper.toCommand(request);
        updatePasswordUseCase.updatePassword(userId, command);
    }


    @Override
    public void assignUserRole(UUID userId, UserRoleUpdateRequest request) {
        AssignRoleCommand command = accountMapper.toCommand(request);
        assignRoleUseCase.assignRole(userId, command);
    }

    @Override
    public void updateUserLockStatus(UUID userId, UserLockUpdateRequest request) {
        UpdateLockStatusCommand command = accountMapper.toCommand(request);
        updateLockStatusUseCase.updateLocked(userId, command);
    }

    @Override
    public CursorResponseUserDto getUsers(CursorRequestUserDto request) {
        return getAccountUseCase.get(request);
    }

    @Override
    public UserDto getUser(String userId) {
        return getAccountUseCase.get(userId);
    }

    @Override
    public UserDto updateProfile(String userId, UserUpdateRequest request, @Nullable MultipartFile image) {
        UpdateProfileCommand command = profileMapper.toCommand(userId, request, image);
        Profile updated = updateProfileUseCase.update(command);
        return getAccountUseCase.get(updated.getAccountId().toString());
    }

    @Override
    public SessionDetails getSessionDetails(UUID watcherId, MoplUserDetails userDetails) {
        UserDto userDto = userDetails.getUserDto();

        List<WatchingSession> watchingSessions = getWatchingSessionUseCase.get(watcherId);
        if  (watchingSessions.isEmpty()) {
            throw WatchingSessionNotFoundException.fromWatcherId(watcherId);
        }

        WatchingSession watchingSession =  watchingSessions.getFirst();
        // liveChatId == contentId 입니다.
        Content content = getContentUseCase.get(watchingSession.getLiveChatId());
        ContentResult contentResult = contentMapper.toContentResult(content);
        UserSummary watcher = new UserSummary(userDto.id(), userDto.name(), userDto.profileImageUrl());

        return new SessionDetails(
                watchingSession.getId(),
                watchingSession.getCreatedAt(),
                watcher,
                contentResult
        );
    }
}