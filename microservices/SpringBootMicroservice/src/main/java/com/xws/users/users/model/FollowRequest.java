package com.xws.users.users.model;

import com.xws.users.users.model.roles.RegularUser;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class FollowRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date dateOfCreation;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RegularUser requster;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RegularUser accountToFollow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public RegularUser getRequster() {
        return requster;
    }

    public void setRequster(RegularUser requster) {
        this.requster = requster;
    }

    public RegularUser getAccountToFollow() {
        return accountToFollow;
    }

    public void setAccountToFollow(RegularUser accountToFollow) {
        this.accountToFollow = accountToFollow;
    }
}
