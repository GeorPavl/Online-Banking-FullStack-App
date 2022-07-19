package com.serverside.helpers;

import com.serverside.model.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerAdvisor {

    @ModelAttribute("registerUser")
    public User getUserDefaults() {
        return new User();
    }
}
