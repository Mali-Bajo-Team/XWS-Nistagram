package com.xws.users.users.model;

import com.xws.users.users.model.roles.RegularUser;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class FollowRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date dateOfCreation;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RegularUser> requster;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RegularUser> accountToFollow;

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

    public List<RegularUser> getRequster() {
        return requster;
    }

    public void setRequster(List<RegularUser> requster) {
        this.requster = requster;
    }

    public List<RegularUser> getAccountToFollow() {
        return accountToFollow;
    }

    public void setAccountToFollow(List<RegularUser> accountToFollow) {
        this.accountToFollow = accountToFollow;
    }
}
