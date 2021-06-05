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

    @OneToMany(fetch = FetchType.LAZY)
    private List<RegularUser> inRelationship;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RegularUser> outRelationship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public List<RegularUser> getInRelationship() {
        return inRelationship;
    }

    public void setInRelationship(List<RegularUser> inRelationship) {
        this.inRelationship = inRelationship;
    }

    public List<RegularUser> getOutRelationship() {
        return outRelationship;
    }

    public void setOutRelationship(List<RegularUser> outRelationship) {
        this.outRelationship = outRelationship;
    }
}
