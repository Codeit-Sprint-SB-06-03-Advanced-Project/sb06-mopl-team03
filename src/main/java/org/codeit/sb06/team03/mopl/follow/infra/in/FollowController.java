package org.codeit.sb06.team03.mopl.follow.infra.in;

import org.codeit.sb06.team03.mopl.common.security.MoplUserDetails;
import org.codeit.sb06.team03.mopl.follow.application.in.*;
import org.codeit.sb06.team03.mopl.follow.infra.FollowMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController implements FollowApi {

    private final ToggleFollowUseCase toggleFollowUseCase;
    private final GetFollowUseCase getFollowUseCase;
    private final FollowMapper mapper;

    public FollowController(
            ToggleFollowUseCase toggleFollowUseCase,
            GetFollowUseCase getFollowUseCase,
            FollowMapper mapper
    ) {
        this.toggleFollowUseCase = toggleFollowUseCase;
        this.getFollowUseCase = getFollowUseCase;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<FollowDto> postFollows(@RequestBody FollowRequest request, @AuthenticationPrincipal MoplUserDetails userDetails) {
        FollowCommand command = mapper.toCommand(request, userDetails.getId());
        FollowDto response = toggleFollowUseCase.follow(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/followed-by-me")
    public ResponseEntity<Boolean> getFollowsFollowedByMe(@RequestParam String followeeId, @AuthenticationPrincipal MoplUserDetails userDetails) {
        FollowQuery query = mapper.toQuery(followeeId, userDetails.getId());
        boolean response = getFollowUseCase.followedByMe(query);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/count")
    public ResponseEntity<Long> getFollowersCount(@RequestParam String followeeId) {
        long response = getFollowUseCase.count(followeeId);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> deleteFollows(@PathVariable String followId, @AuthenticationPrincipal MoplUserDetails userDetails) {
        UnfollowCommand command = mapper.toCommand(followId, userDetails.getId());
        toggleFollowUseCase.unfollow(command);
        return ResponseEntity.noContent().build();
    }
}
