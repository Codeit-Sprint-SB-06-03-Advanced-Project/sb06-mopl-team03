package org.codeit.sb06.team03.mopl.auth.infra.in;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

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

    @Override
    @GetMapping("/csrf-token")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}