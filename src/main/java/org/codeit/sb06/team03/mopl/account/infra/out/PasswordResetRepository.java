package org.codeit.sb06.team03.mopl.account.infra.out;

import org.codeit.sb06.team03.mopl.account.domain.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

    Optional<PasswordReset> findById(UUID id);

    void deleteById(UUID id);

}
