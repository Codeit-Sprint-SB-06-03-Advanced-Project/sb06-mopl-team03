package org.codeit.sb06.team03.mopl.bff;

import org.codeit.sb06.team03.mopl.common.SessionDetails;
import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.user.infra.in.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface BffUserService {

    UserDto registerAccount(UserCreateRequest request);

    void assignUserRole(UUID userId, UserRoleUpdateRequest request);

    void updateUserLockStatus(UUID userId, UserLockUpdateRequest request);

    void updatePassword(String userId, PasswordUpdateRequest request);

    CursorResponseUserDto getUsers(CursorRequestUserDto request);

    UserDto getUser(String userId);

    UserDto updateProfile(String userId, UserUpdateRequest request, @Nullable MultipartFile image);

    SessionDetails getSessionDetails(UUID watcherId, MoplUserDetails userDetails);
}
