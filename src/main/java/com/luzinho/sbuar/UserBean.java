package com.luzinho.sbuar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class UserBean {

    @Getter @Setter
    String name;
    @Getter @Setter
    String lastName;
    @Getter @Setter
    String fullName;
}
