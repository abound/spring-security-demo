package org.example.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: MaiYu
 * @Date: Create in 13:06 2020/12/20
 */
@RestController
@RequestMapping(value = "/myRole", produces = "text/html;charset=UTF-8")
public class MyRoleUser {


    @PostMapping(value = "/test")
    public String valueName(){

        return "success";
    }



}
