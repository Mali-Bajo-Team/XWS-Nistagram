package com.xws.users.users.model;

import com.xws.users.users.model.roles.Agent;
import com.xws.users.users.model.roles.RegularUser;

import javax.persistence.*;

@Entity
public class PrivacySettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isPrivate;

    @Column
    private boolean allowMessagesFromNotFollowed;

    @Column
    private boolean allowTags;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RegularUser regularUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isAllowMessagesFromNotFollowed() {
        return allowMessagesFromNotFollowed;
    }

    public void setAllowMessagesFromNotFollowed(boolean allowMessagesFromNotFollowed) {
        this.allowMessagesFromNotFollowed = allowMessagesFromNotFollowed;
    }

    public boolean isAllowTags() {
        return allowTags;
    }

    public void setAllowTags(boolean allowTags) {
        this.allowTags = allowTags;
    }

    public RegularUser getRegularUser() {
        return regularUser;
    }

    public void setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
    }
}
