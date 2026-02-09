package org.codeit.sb06.team03.mopl.user.infra.out;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "profiles")
public class JpaProfile {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Version
    @Column(name = "version")
    private short version;

    public UUID id() {
        return id;
    }

    public JpaProfile setId(UUID id) {
        this.id = id;
        return this;
    }

    public String name() {
        return name;
    }

    public JpaProfile setName(String name) {
        this.name = name;
        return this;
    }

    public short version() {
        return version;
    }

    public JpaProfile setVersion(short version) {
        this.version = version;
        return this;
    }
}
