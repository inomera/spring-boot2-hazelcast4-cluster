package com.app.hzcluster.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CredentialInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    @ToString.Exclude
    private String password;
    private List<String> authorities = new ArrayList<>();
}
