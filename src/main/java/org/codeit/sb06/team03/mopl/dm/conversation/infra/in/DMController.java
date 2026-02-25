package org.codeit.sb06.team03.mopl.dm.conversation.infra.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.bff.BffDMService;
import org.codeit.sb06.team03.mopl.dm.conversation.infra.in.request.ConversationCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/conversations")
public class DMController implements DMApi{

    private final BffDMService bffDMService;

    @Override
    @GetMapping
    public ResponseEntity<CursorResponseConversationDto> getConversations(@ModelAttribute CursorRequestConversationDto request) {
        CursorResponseConversationDto response = bffDMService.getConversations(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<ConversationDto> postConversation(@RequestBody(required = true) @Valid ConversationCreateRequest request) {
        ConversationDto response = bffDMService.postConversation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/{conversationId}/direct-messages/{directMessageId}/read")
    public ResponseEntity<Void> postReadDirectMessage(
            @PathVariable String conversationId,
            @PathVariable String directMessageId) {
        bffDMService.postReadDirectMessage(conversationId, directMessageId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationDto> getConversation(@PathVariable String conversationId) {
        ConversationDto response = bffDMService.getConversation(conversationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/{conversationId}/direct-messages")
    public ResponseEntity<CursorResponseDirectMessageDto> getDirectMessages(
            @PathVariable String conversationId,
            @ModelAttribute CursorRequestDirectMessageDto request) {
        CursorResponseDirectMessageDto response = bffDMService.getDirectMessages(conversationId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/with")
    public ResponseEntity<ConversationDto> getConversationWith(@RequestParam String userId) {
        ConversationDto response = bffDMService.getConversationWith(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
