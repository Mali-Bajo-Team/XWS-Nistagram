package com.xws.users.users.model;
import com.xws.users.users.model.enums.RequestStatus;
import com.xws.users.users.model.roles.Agent;

import javax.persistence.*;

@Entity
public class RegistrationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private RequestStatus requestStatus;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Agent agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
