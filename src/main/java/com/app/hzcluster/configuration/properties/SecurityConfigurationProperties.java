package com.app.hzcluster.configuration.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SecurityConfigurationProperties {
    private int bcryptPasswordStrength = 11;
    private List<CredentialInfo> users = new ArrayList<>();
}
