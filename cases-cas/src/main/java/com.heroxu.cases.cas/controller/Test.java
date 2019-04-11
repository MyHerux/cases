package com.heroxu.cases.cas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping(value = "/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return "test";
    }

    @GetMapping(value = "/test2")
    public String test2(HttpServletRequest request) {
        HttpSession session = request.getSession();
        request.getSession().setAttribute("test","test");
        return "test";
    }

}
