package org.codeit.sb06.team03.mopl.follow.infra.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController implements FollowApi {

    @Override
    @PostMapping
    public ResponseEntity<FollowDto> postFollows(@RequestBody FollowRequest request) {
        return null;
    }

    @Override
    @GetMapping("/followed-by-me")
    public ResponseEntity<Boolean> getFollowsFollowedByMe(@RequestParam String followeeId) {
        return null;
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> getFollowersCount(@RequestParam String followeeId) {
        return null;
    }

    @Override
    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> deleteFollows(@PathVariable String followId) {
        return null;
    }
}
