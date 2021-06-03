package com.xws.users.users.model;

import com.xws.users.users.model.enums.RelationshipType;
import com.xws.users.users.model.roles.RegularUser;

import javax.persistence.*;
import java.util.List;

@Entity
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private RelationshipType relationshipType;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RegularUser inRelationship;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private RegularUser outRelationship;

}
