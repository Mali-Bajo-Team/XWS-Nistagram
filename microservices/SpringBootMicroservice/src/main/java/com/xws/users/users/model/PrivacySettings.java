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
}
