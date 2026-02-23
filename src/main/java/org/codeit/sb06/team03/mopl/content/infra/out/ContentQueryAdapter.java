package org.codeit.sb06.team03.mopl.content.infra.out;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.common.ContentMapper;
import org.codeit.sb06.team03.mopl.common.ContentResult;
import org.codeit.sb06.team03.mopl.content.Content;
import org.codeit.sb06.team03.mopl.liveChat.application.out.LiveChatContentQueryPort;
import org.codeit.sb06.team03.mopl.content.domain.exception.ContentNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContentQueryAdapter implements LiveChatContentQueryPort {

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    @Override
    public ContentResult findById(UUID contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> ContentNotFoundException.fromId(contentId));

        return contentMapper.toContentResult(content);
    }
}
