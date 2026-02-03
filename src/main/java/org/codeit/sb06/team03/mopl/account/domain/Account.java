package org.codeit.sb06.team03.mopl.account.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.vo.Email;
import org.codeit.sb06.team03.mopl.account.domain.vo.Password;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

import static org.codeit.sb06.team03.mopl.account.domain.event.AccountEvent.AccountRegisteredEvent;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account extends AbstractAggregateRoot<Account> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    private short version;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    public static Account create(Email email, Password password) {
        var account = new Account();
        account.email = email;
        account.password = password;
        account.registerEvent(new AccountRegisteredEvent());
        return account;
    }
}
