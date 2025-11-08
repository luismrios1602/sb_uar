package com.luzinho.sbuar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chapter5/user")
public class UserController {

    @Value("${user_name: Luis}")
    String nameValue;
    @Value("${user_lastName: Rios}")
    String lastNameValue;
    @Value("${user_fullName: ${user_name} ${user_lastName} } ")
    String fullName;

    final UserConfigProps userConfigProps;
    final UserBean userBean;

    UserController(UserConfigProps userProps, UserBean userBean){
        this.userConfigProps = userProps;
        this.userBean = userBean;
    }

    @GetMapping("nameValue")
    String getNameValue(){
        return nameValue;
    }

    @GetMapping("lastNameValue")
    String getLastNameValue(){
        return lastNameValue;
    }

    @GetMapping("fullNameValue")
    String getFullName(){
        return fullName;
    }

    @GetMapping("nameProp")
    String getNameProp(){
        return userConfigProps.getName();
    }

    @GetMapping("lastNameProp")
    String getLastNameProp(){
        return userConfigProps.getLastName();
    }

    @GetMapping("fullNameProp")
    String getFullNameProp(){
        return userConfigProps.getFullName();
    }

    @GetMapping("fullNameBean")
    String getFullNameBean(){
        return userBean.getFullName();
    }

}
