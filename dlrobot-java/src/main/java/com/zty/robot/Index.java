package com.zty.robot;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class Index {

    @RequestMapping("/")
    public void index(Model model, HttpServletResponse response) throws IOException {


        response.sendRedirect("/robot/index.html");
    }
}
