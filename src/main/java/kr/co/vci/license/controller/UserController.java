package kr.co.vci.license.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.vci.license.service.UserService;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/login")
    public String login() {
        System.out.println("TEST : " + service.testSelect());

        return "login";
    }

}
