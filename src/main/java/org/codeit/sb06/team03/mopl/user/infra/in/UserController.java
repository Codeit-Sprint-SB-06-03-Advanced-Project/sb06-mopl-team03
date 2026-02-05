package org.codeit.sb06.team03.mopl.user.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.AccountService;
import org.codeit.sb06.team03.mopl.account.infra.in.PasswordUpdateRequest;
import org.codeit.sb06.team03.mopl.bff.BffUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final BffUserService bffUserService;
    private final AccountService accountService;

    @Override
    @PostMapping
    public ResponseEntity<UserDto> postUsers(@RequestBody UserCreateRequest request) {
        UserDto response = bffUserService.registerAccount(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID userId, @RequestBody PasswordUpdateRequest request){
        accountService.updatePassword();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
