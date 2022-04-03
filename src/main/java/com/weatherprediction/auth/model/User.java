package com.weatherprediction.auth.model;

import com.weatherprediction.auth.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Table(name = "_user")
@Entity
@Getter
@Setter
public class User extends AbstractPersistable<Long> {

    @Column
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Type(type = "postgres_enum")
    protected Role role;

    @Column(unique = true)
    protected String username;
}
