package org.codeit.sb06.team03.mopl.account.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codeit.sb06.team03.mopl.account.domain.entity.PasswordReset;
import org.codeit.sb06.team03.mopl.account.domain.vo.EmailAddress;
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
@Table(name = "accounts")
public class Account extends AbstractAggregateRoot<Account> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Version
    @Column(name = "version", nullable = false)
    private short version;

    @Embedded
    private EmailAddress emailAddress;

    @Embedded
    private Password password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "locked", nullable = false)
    private boolean locked;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private PasswordReset passwordReset;

    public static Account create(EmailAddress emailAddress, Password password) {
        var account = new Account();
        account.emailAddress = emailAddress;
        account.password = password;
        account.role = Role.USER;
        account.locked = false;
        account.registerEvent(new AccountRegisteredEvent());
        return account;
    }
}
