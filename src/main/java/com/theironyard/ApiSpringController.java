package com.theironyard;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by zach on 12/11/15.
 */
@RestController
public class ApiSpringController {
    static final String SAMPLE_URL = "http://gturnquist-quoters.cfapps.io/api/random";

    Facebook facebook;

    @Inject
    public ApiSpringController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping("/quote")
    public HashMap getQuote() {
        RestTemplate query = new RestTemplate();
        HashMap result = query.getForObject(SAMPLE_URL, HashMap.class);
        String type = (String) result.get("type");
        if (type.equals("success")) {
            HashMap value = (HashMap) result.get("value");
            return value;
        }
        return result;
    }

    @RequestMapping("/profile")
    public User getUser(HttpServletResponse response) throws IOException {
        try {
            User user = facebook.userOperations().getUserProfile();
            return user;
        } catch (Exception e) {
            response.sendRedirect("/connect/facebook");
        }

        return null;
    }
}
