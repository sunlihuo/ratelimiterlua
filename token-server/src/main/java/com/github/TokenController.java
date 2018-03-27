package com.github;

import com.github.tokenclient.RateLimitClient;
import com.github.tokenclient.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Hello world!
 */
@RestController
public class TokenController {

    @Autowired
    RateLimitClient rateLimitClient;

    @GetMapping(value = "/getToken")
    @ResponseBody
    public String getToken() {
        //ratelimit:skynet
        Token token = rateLimitClient.accquireToken("skynet");
        if (token.isSuccess()) {
            return "success";
        } else {
            return "failed";
        }
    }

    @GetMapping(value = "/initToken")
    @ResponseBody
    public String initToken() {
        //ratelimit:skynet
        Token token = rateLimitClient.initToken("skynet");
        if (token.isSuccess()) {
            return "success";
        } else {
            return "failed";
        }
    }
}
