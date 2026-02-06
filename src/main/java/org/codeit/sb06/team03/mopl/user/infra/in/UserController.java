package org.codeit.sb06.team03.mopl.user.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.infra.in.PasswordUpdateRequest;
import org.codeit.sb06.team03.mopl.bff.BffUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final BffUserService bffUserService;

    @Override
    @PostMapping
    public ResponseEntity<UserDto> postUsers(@RequestBody UserCreateRequest request) {
        UserDto response = bffUserService.registerAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable String userId, @RequestBody PasswordUpdateRequest request){
        bffUserService.updatePassword(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<Void> patchUsersRole(
            @PathVariable(name = "userId") String userId,
            @RequestBody UserRoleUpdateRequest request
    ) {
        bffUserService.assignUserRole(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<CursorResponseUserDto> getUsers(@ModelAttribute CursorRequestUserDto request) {
        CursorResponseUserDto response = bffUserService.getUsers(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
