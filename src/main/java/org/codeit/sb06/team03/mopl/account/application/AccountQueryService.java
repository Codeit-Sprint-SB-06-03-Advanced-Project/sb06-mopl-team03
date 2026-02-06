package org.codeit.sb06.team03.mopl.account.application;

import lombok.RequiredArgsConstructor;
import org.codeit.sb06.team03.mopl.account.application.in.GetAccountUseCase;
import org.codeit.sb06.team03.mopl.account.application.out.LoadAccountPort;
import org.codeit.sb06.team03.mopl.user.infra.in.CursorRequestUserDto;
import org.codeit.sb06.team03.mopl.user.infra.in.CursorResponseUserDto;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.codeit.sb06.team03.mopl.user.infra.in.CursorResponseUserDto.SortOrder;

@RequiredArgsConstructor
@Service
public class AccountQueryService implements GetAccountUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public CursorResponseUserDto get(CursorRequestUserDto request) {
        final List<UserDto> userDtos = loadAccountPort.findAll(request);
        final Integer limit = request.limit();
        final List<UserDto> data = obtainData(userDtos, limit);
        final String nextCursor = obtainNextCursor(userDtos, limit, request.sortDirection());
        final String nextIdAfter = obtainNextIdAfter(userDtos, limit);
        final Boolean hasNext = obtainHasNext(userDtos, limit);
        final Long totalCount = loadAccountPort.count(request);
        final String sortBy = request.sortBy();
        final SortOrder sortOrder = SortOrder.parse(request.sortDirection());

        return new CursorResponseUserDto(
                data,
                nextCursor,
                nextIdAfter,
                hasNext,
                totalCount,
                sortBy,
                sortOrder
        );
    }

    private static List<UserDto> obtainData(List<UserDto> userDtos, Integer limit) {
        int size = calculateSize(userDtos, limit);
        return userDtos.subList(0, size);
    }

    private static int calculateSize(List<UserDto> userDtos, Integer limit) {
        if (userDtos.isEmpty()) {
            return 0;
        }
        if (userDtos.size() <= limit) {
            return userDtos.size();
        }
        return limit;
    }

    @Nullable
    private static String obtainNextCursor(List<UserDto> userDtos, Integer limit, String sortOrder) {
        if (userDtos.size() <= limit) {
            return null;
        }
        return switch (sortOrder) {
            case "name" -> userDtos.getLast().name();
            case "email" -> userDtos.getLast().email();
            case "createdAt" -> userDtos.getLast().createdAt().toString();
            case "isLocked" -> userDtos.getLast().locked().toString();
            default -> userDtos.getLast().role();
        };
    }

    @Nullable
    private static String obtainNextIdAfter(List<UserDto> userDtos, Integer limit) {
        if (userDtos.size() <= limit) {
            return null;
        }
        return userDtos.getLast().id().toString();
    }

    private static Boolean obtainHasNext(List<UserDto> userDtos, Integer limit) {
        if (userDtos.size() > limit) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
