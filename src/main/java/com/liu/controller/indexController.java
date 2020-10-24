package com.liu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 刘浩然
 * @Date: 2020/10/24 1:44
 */
@Controller
public class indexController {
    @RequestMapping("/index")
public String index(){

    return "index";
}
}
