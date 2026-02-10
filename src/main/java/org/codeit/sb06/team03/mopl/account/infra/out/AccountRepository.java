package org.codeit.sb06.team03.mopl.account.infra.out;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.account.domain.Account;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
import org.codeit.sb06.team03.mopl.account.domain.vo.Role;
import org.codeit.sb06.team03.mopl.user.domain.vo.TimeoutImage;
import org.codeit.sb06.team03.mopl.user.infra.in.CursorRequestUserDto;
import org.codeit.sb06.team03.mopl.user.infra.in.UserDto;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.codeit.sb06.team03.mopl.account.domain.QAccount.account;

public interface AccountRepository extends QuerydslJpaRepository<Account, UUID> {

    boolean existsByEmailAddress(EmailAddress emailAddress);

    Account findByEmailAddress(EmailAddress emailAddress);

    default List<UserDto> findAll(CursorRequestUserDto query) {
        final String emailLike = query.emailLike();
        final String roleEqual = query.roleEqual();
        final Boolean isLocked = query.isLocked();
        final String cursor = query.cursor();
        final String idAfter = query.idAfter();
        final int limit = query.limit();
        final String sortDirection = query.sortDirection();
        final String sortBy = query.sortBy();

        return findAll(
                emailLike,
                roleEqual,
                isLocked,
                cursor,
                idAfter,
                limit,
                sortDirection,
                sortBy
        );
    }

    private List<UserDto> findAll(
            String emailLike,
            String roleEqual,
            Boolean isLocked,
            String cursor,
            String idAfter,
            int limit,
            String sortDirection,
            String sortBy
    ) {
        Predicate[] predicates = {
                emailLikePredicate(emailLike),
                roleEqualPredicate(roleEqual),
                isLockedPredicate(isLocked),
                cursorExpressionPredicate(cursor, idAfter, sortDirection, sortBy)
        };

        sortDirection = sortDirection.equalsIgnoreCase("ASCENDING") ? "ASC" : "DESC";

        List<Account> result = select(account)
                .from(account)
                .innerJoin(account.profile).fetchJoin()
                .leftJoin(account.profile.timeoutImage).fetchJoin()
                .where(predicates)
                .orderBy(orderByExpressions(sortDirection, sortBy))
                .limit(1L + limit)
                .fetch();

        return result.stream()
                .map(account -> {
                    TimeoutImage timeoutImage = account.getProfile().getTimeoutImage();
                    return new UserDto(
                            account.getId(),
                            account.getCreatedAt(),
                            account.getEmailAddress().value(),
                            account.getProfile().getName(),
                            timeoutImage == null ? null : timeoutImage.getPresignedUrl(),
                            account.getRole().name(),
                            account.isLocked()
                    );
                })
                .toList();

//
//        return select(accountDtoProjection())
//                .from(account)
//                .innerJoin(account.profile)
//                .leftJoin(account.profile.timeoutImage)
//                .where(predicates)
//                .orderBy(orderByExpressions(sortDirection, sortBy))
//                .limit(1L + limit)
//                .fetch();
    }

    private static Expression<UserDto> accountDtoProjection() {
        return Projections.constructor(
                UserDto.class,
                account.id,
                account.createdAt,
                account.emailAddress.value,
                account.profile.name,
                account.profile.timeoutImage.presignedUrl,
                account.role.stringValue(),
                account.locked
        );
    }

    @Nullable
    private static BooleanExpression emailLikePredicate(@Nullable String emailLike) {
        if (emailLike == null || emailLike.isBlank()) {
            return null;
        }
        return account.emailAddress.value.containsIgnoreCase(emailLike);
    }

    @Nullable
    private static BooleanExpression roleEqualPredicate(@Nullable String roleEqual) {
        return roleEqual == null ? null : account.role.eq(Role.parse(roleEqual));
    }

    @Nullable
    private static BooleanExpression isLockedPredicate(@Nullable Boolean isLocked) {
        return isLocked == null ? null : account.locked.eq(isLocked);
    }

    @Nullable
    private static BooleanExpression cursorExpressionPredicate(
            @Nullable String cursor,
            @Nullable String idAfter,
            String sortDirection,
            String sortBy
    ) {
        if (cursor == null || idAfter == null) {
            return null;
        }

        final UUID idAfterUuid = UUID.fromString(idAfter);
        return switch (sortBy) {
            case "name" -> {
                final String nameCursor = cursor;
                if ("ASCENDING".equalsIgnoreCase(sortDirection)) {
                    yield account.profile.name.gt(nameCursor)
                            .or(account.profile.name.eq(nameCursor).and(account.id.goe(idAfterUuid)));
                }
                yield account.profile.name.lt(nameCursor)
                        .or(account.profile.name.eq(nameCursor).and(account.id.loe(idAfterUuid)));
            }
            case "email" -> {
                final String emailCursor = cursor;
                if ("ASCENDING".equalsIgnoreCase(sortDirection)) {
                    yield account.emailAddress.value.gt(emailCursor)
                            .or(account.emailAddress.value.eq(emailCursor).and(account.id.goe(idAfterUuid)));
                }
                yield account.emailAddress.value.lt(emailCursor)
                        .or(account.emailAddress.value.eq(emailCursor).and(account.id.loe(idAfterUuid)));
            }
            case "createdAt" -> {
                final Instant createdAtCursor = Instant.parse(cursor);
                if ("ASCENDING".equalsIgnoreCase(sortDirection)) {
                    yield account.createdAt.gt(createdAtCursor)
                            .or(account.createdAt.eq(createdAtCursor).and(account.id.goe(idAfterUuid)));
                }
                yield account.createdAt.lt(createdAtCursor)
                        .or(account.createdAt.eq(createdAtCursor).and(account.id.loe(idAfterUuid)));
            }
            case "isLocked" -> {
                final boolean isLockedCursor = Boolean.parseBoolean(cursor);
                if ("ASCENDING".equalsIgnoreCase(sortDirection)) {
                    yield account.locked.gt(isLockedCursor)
                            .or(account.locked.eq(isLockedCursor).and(account.id.goe(idAfterUuid)));
                }
                yield account.locked.lt(isLockedCursor)
                        .or(account.locked.eq(isLockedCursor).and(account.id.loe(idAfterUuid)));
            }
            default -> {
                final Role roleCursor = Role.parse(cursor);
                if ("ASCENDING".equalsIgnoreCase(sortDirection)) {
                    yield account.role.gt(roleCursor)
                            .or(account.role.eq(roleCursor).and(account.id.goe(idAfterUuid)));
                }
                yield account.role.lt(roleCursor)
                        .or(account.role.eq(roleCursor).and(account.id.loe(idAfterUuid)));
            }
        };
    }

    private static OrderSpecifier<?>[] orderByExpressions(String sortDirection, String sortBy) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        orderSpecifiers.add(orderByCursor(sortDirection, sortBy));
        final var orderById = new OrderSpecifier<>(Order.valueOf(sortDirection), account.id);
        orderSpecifiers.add(orderById);

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private static OrderSpecifier<?> orderByCursor(String sortDirection, String sortBy) {
        return switch (sortBy) {
            case "name" -> new OrderSpecifier<>(Order.valueOf(sortDirection), account.profile.name);
            case "email" -> new OrderSpecifier<>(Order.valueOf(sortDirection), account.emailAddress.value);
            case "createdAt" -> new OrderSpecifier<>(Order.valueOf(sortDirection), account.createdAt);
            case "isLocked" -> new OrderSpecifier<>(Order.valueOf(sortDirection), account.locked);
            default -> new OrderSpecifier<>(Order.valueOf(sortDirection), account.role);
        };
    }

    default Long count(CursorRequestUserDto query) {
        final String emailLike = query.emailLike();
        final String roleEqual = query.roleEqual();
        final Boolean isLocked = query.isLocked();

        return count(emailLike, roleEqual, isLocked);
    }

    private Long count(String emailLike, String roleEqual, Boolean isLocked) {
        Long count = select(account.count())
                .from(account)
                .where(
                        emailLikePredicate(emailLike),
                        roleEqualPredicate(roleEqual),
                        isLockedPredicate(isLocked)
                )
                .fetchOne();

        return count == null ? 0 : count;
    }

    default Optional<UserDto> findById(String accountId) {
        final UUID accountIdUuid = UUID.fromString(accountId);
        Account result = select(account)
                .from(account)
                .innerJoin(account.profile).fetchJoin()
                .leftJoin(account.profile.timeoutImage).fetchJoin()
                .where(account.id.eq(accountIdUuid))
                .fetchFirst();

        TimeoutImage timeoutImage = result.getProfile().getTimeoutImage();
        return Optional.of(new UserDto(
                result.getId(),
                result.getCreatedAt(),
                result.getEmailAddress().value(),
                result.getProfile().getName(),
                timeoutImage == null ? null : timeoutImage.getPresignedUrl(),
                result.getRole().name(),
                result.isLocked()
        ));


//        UserDto userDto = select(accountDtoProjection())
//                .from(account)
//                .innerJoin(account.profile)
//                .leftJoin(account.profile.timeoutImage)
//                .where(account.id.eq(accountIdUuid))
//                .fetchFirst();
//
//        return Optional.ofNullable(userDto);
    }
}
