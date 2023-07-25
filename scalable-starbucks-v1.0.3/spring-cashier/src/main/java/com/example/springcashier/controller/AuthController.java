package com.example.springcashier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springcashier.login.Role;
import com.example.springcashier.login.User;
import com.example.springcashier.service.UserService;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import java.net.InetAddress;
import java.util.List;

@Slf4j
@Controller
public class AuthController {
    @Autowired
    UserService userService;

    private void addServerInfoToModel(Model model) {
        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
        }
        model.addAttribute("server", host_name + "/" + server_ip);
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model, @Valid User user, BindingResult bindingResult) {
        return "auth/login";
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String register(Model model) {
        return "auth/register";
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(Model model, @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors and try again.");
            model.addAttribute("bindingResult", bindingResult);
            return "auth/register";
        }
        List<Object> userPresentObj = userService.isUserPresent(user);

        // User is present
        if ((Boolean) userPresentObj.get(0)) {
            model.addAttribute("successMessage", userPresentObj.get(1));
            return "auth/register";
        } else {
            // User is not present
            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }
            user.setRole(user.getRole());
            userService.saveUser(user);
            model.addAttribute("successMessage", "User registered successfully!");
            log.info("User registered successfully!", user.getEmail());

            return "auth/login";
        }

    }

    @RequestMapping(value = { "/about-us" }, method = RequestMethod.GET)
    public String aboutUs(Model model) {
        return "about-us";
    }
}
