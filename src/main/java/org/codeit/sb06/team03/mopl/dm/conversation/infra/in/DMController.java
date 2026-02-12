package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
public class DMController implements DMApi{

    @Override
    @GetMapping
    public ResponseEntity<CursorResponseConversationDto> getConversations(@Valid CursorRequestConversationDto request) {
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<ConversationDto> postConversation(@RequestBody(required = true) @Valid ConversationCreateRequest request) {
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/{conversationId}/direct-messages/{directMessageId}/read")
    public ResponseEntity<Void> postReadDirectMessage(
            @PathVariable String conversationId,
            @PathVariable String directMessageId) {
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationDto> getConversation(@PathVariable String conversationId) {
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/{conversationId}/direct-messages")
    public ResponseEntity<CursorResponseDirectMessageDto> getDirectMessages(
            @PathVariable String conversationId,
            @Valid CursorRequestDirectMessageDto request) {
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/with")
    public ResponseEntity<ConversationDto> getConversationWith(@RequestParam String userId) {
        return ResponseEntity.ok().build();
    }
}
