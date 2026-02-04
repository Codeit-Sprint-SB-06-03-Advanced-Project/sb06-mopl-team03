package org.codeit.sb06.team03.mopl.user.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final BffUserService bffUserService;

    @Override
    @PostMapping
    public ResponseEntity<UserDto> postUsers(@RequestBody UserCreateRequest request) {
        UserDto response = bffUserService.registerAccount(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
