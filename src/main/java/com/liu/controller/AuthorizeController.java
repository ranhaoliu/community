package com.liu.controller;

import com.liu.dto.AccessTokenDto;
import com.liu.dto.GithubUser;
import com.liu.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 刘浩然
 * @Date: 2020/10/24 11:14
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private  String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code
            , @RequestParam(name="state") String state, HttpServletRequest httpRequest){

        AccessTokenDto accessTokenDto = new AccessTokenDto();// shift + Enter 快速换行光标移动到最前面
        accessTokenDto.setState(state);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);//ctrl +alt +v 快速把new 的对象放到外边
        GithubUser user = githubProvider.getUser(accessToken);
//        System.out.println(user);
//        System.out.println(user.getName());
        if(user!=null){
            //登录成功，写入Sesson 和cookie
            httpRequest.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            // 登录失败，重新登录
            return "redirect:/";
        }

    }
//    @GetMapping("/test")
//    public String testGetUser(){
//        GithubUser user = githubProvider.getUser("51b1ab278336766e3e1025c2abb8730e6b073b26");
//            System.out.println(user);
//        return "";
//    }
}
