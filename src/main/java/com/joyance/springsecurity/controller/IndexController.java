package com.joyance.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
    	ModelAndView mv = new ModelAndView("login");
    	if (error != null) {
    		mv.addObject("error", "用户名或密码错误");
        }
        return mv;
    }
}
