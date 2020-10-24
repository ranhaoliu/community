package com.liu.provider;
import com.alibaba.fastjson.JSON;
import com.liu.dto.AccessTokenDto;
import com.liu.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

/**
 * @Author: 刘浩然
 * @Date: 2020/10/24 11:26
 */
@Component
@Slf4j
public class GithubProvider {
    //拿着code 去换token
    //access_token=f0cdde3f28eeeb935fc4a96a712f089622090448&scope=user&token_type=bearer
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //返回一个token
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
//            log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }
    //拿着token 去访问github 获取用户信息
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user" )
                .addHeader("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println("getUser 中的信息"+string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }


}
