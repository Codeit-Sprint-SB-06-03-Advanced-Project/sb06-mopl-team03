package org.codeit.sb06.team03.mopl.liveChat.infra.in.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DestinationUtils {

    private static final Pattern liveChatWatchSubDestinationPattern =
            Pattern.compile("^/sub/contents/[0-9a-zA-Z-]+/watch$");
    private static final Pattern liveChatSubDestinationPattern =
            Pattern.compile("^/sub/contents/[0-9a-zA-Z-]+/chat$");
    private static final Pattern liveChatPubDestinationPattern =
            Pattern.compile("^/pub/contents/[0-9a-zA-Z-]+/chat$");

    public static final String liveChatSendResponseDestinationFormat = "/sub/contents/%s/chat";

    public static String extractContentId(String destination) {
        String[] parts = destination.split("/"); // "", "sub", "content", "[contentId]", "watch"
        if (parts.length < 5 || !StringUtils.hasText(parts[3])) {
            return null;
        }
        return parts[3];
    }

    public static boolean matchWatchSubDestination(String destination) {
        return liveChatWatchSubDestinationPattern.matcher(destination).matches();
    }

    public static boolean matchChatSubDestination(String destination) {
        return liveChatSubDestinationPattern.matcher(destination).matches();
    }

    public static boolean matchPubDestination(String destination) {
        return liveChatPubDestinationPattern.matcher(destination).matches();
    }

    public static boolean isContentDestination(String destination) {
        return destination.startsWith("/sub/contents/") || destination.startsWith("/pub/contents/");
    }
}
