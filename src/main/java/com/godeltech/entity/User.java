package com.godeltech.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "user_account")
public class User extends AbstractEntity {
    @Column
    private String userName;
    @Column
    private String password;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JsonManagedReference
    private Role role;
}
