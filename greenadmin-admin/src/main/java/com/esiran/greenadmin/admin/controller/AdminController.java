package com.esiran.greenadmin.admin.controller;


import com.esiran.greenadmin.admin.entity.UsernamePasswordInputDTO;
import com.esiran.greenadmin.framework.annotation.PageViewHandleError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
public class AdminController extends CURDBaseController {
    private static final Gson gson = new GsonBuilder().create();

    public AdminController() {
    }

    @GetMapping
    public String index() {
        return redirect("/home");
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "admin/index";
    }

    @GetMapping("/demo")
    public String demo(Model model) {
        return "admin/demo/index";
    }

    @GetMapping("/merchantInfo")
    @RequiresRoles("admin")
    @RequiresPermissions("system:admin")
    public String merchantInfo(Model model) {

        return "admin/merchantInfo";
    }

    @GetMapping("/login")
    @PageViewHandleError
    public String login() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return redirect("/home");
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid UsernamePasswordInputDTO inputDTO) {
        UsernamePasswordToken token = new UsernamePasswordToken(
                inputDTO.getUsername(), inputDTO.getPassword());
        SecurityUtils.getSubject().login(token);
        return redirect("/home");
    }

    @PostMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return redirect("/login");
    }


    @GetMapping("/unAuth")
    public String unAuth(){
        return "admin/unauth";
    }
}