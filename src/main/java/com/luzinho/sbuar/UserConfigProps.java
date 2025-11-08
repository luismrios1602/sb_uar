package com.luzinho.sbuar;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user-conf-prop")
public class UserConfigProps {
    @Getter @Setter
    String name;
    @Getter @Setter
    String lastName;
    @Getter @Setter
    String fullName;
}
