package org.codeit.sb06.team03.mopl.content.infra.in.mock;

import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contents")
public class ContentMockController {

    @GetMapping
    public ResponseEntity<ContentCursorResponse> get(@ModelAttribute ContentCursorRequest contentCursorRequest) {
        ContentResult contentResult =
                new ContentResult(
                        UUID.fromString("a1e21d98-bc59-4682-a78d-cd556457f482"),
                        "movie",
                        "어벤져스 인피니티 워",
                        "액션 블록버스터 영화",
                        "http://localhost:8080/favicon.svg",
                        List.of(),
                        0,
                        0
                );

        ContentCursorResponse response = new ContentCursorResponse(
                List.of(contentResult),
                false,
                "ASCENDING",
                "createdAt",
                1
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentResult> get(@PathVariable String contentId) {
        ContentResult contentResult =
                new ContentResult(
                        UUID.fromString("a1e21d98-bc59-4682-a78d-cd556457f482"),
                        "movie",
                        "어벤져스 인피니티 워",
                        "액션 블록버스터 영화",
                        "http://localhost:8080/favicon.svg",
                        List.of(),
                        0,
                        0
                );

        return ResponseEntity.ok(contentResult);
    }
}
