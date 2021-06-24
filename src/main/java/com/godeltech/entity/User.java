package com.godeltech.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_account")
public class User extends AbstractEntity {
    @Column
    private String userName;
    @Column
    private String password;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Role.class)
    private Role role;

    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
