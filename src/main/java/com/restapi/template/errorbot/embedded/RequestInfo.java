package com.restapi.template.errorbot.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@AllArgsConstructor
public class RequestInfo {
    @Column(name = "PATH", length = 2048)
    private String path;

    @Column(name = "PARAMETER_MAP", columnDefinition = "TEXT")
    private String parameterMap;

    @Column(name = "HEADER_MAP", columnDefinition = "TEXT")
    private String headerMap;

    @Column(name = "BODY", columnDefinition = "TEXT")
    private String body;

    @Column(name = "AGENT_DETAIL", columnDefinition = "TEXT")
    private String agentDetail;

    public RequestInfo() {

    }
}
