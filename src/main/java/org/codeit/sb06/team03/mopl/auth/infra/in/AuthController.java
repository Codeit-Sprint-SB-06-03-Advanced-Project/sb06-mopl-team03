package org.codeit.sb06.team03.mopl.auth.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi{

    private final BffAuthService bffAuthService;

    @Override
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        bffAuthService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
