package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    AppUserRepository users;

    @Autowired
    UserRoleRepository roles;

    @Autowired
    FriendRepository friend;

    @Autowired
    FriendService friends;


    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/login") public String showLogin(){
        return "login";
    }

    @GetMapping("/register")
    public String regiter(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") AppUser user, BindingResult result, Model model) {

        AppUser currentUser = ((AppUser)result.getModel().get("user"));

        if(users.existsByUsername(currentUser.getUsername())){
            model.addAttribute("usernameErr", users.existsByUsername(currentUser.getUsername()));
            return "register";
        }

        UserRole role = new UserRole("USER");
        roles.save(role);

        user.addRole(role);
        users.save(user);
        return "redirect:/";
    }
    @RequestMapping("/detail/{id}")
    public String showJobs(@PathVariable("id") long id, Model model) {
        model.addAttribute("friend", friend.findById(id).get());
        return "/friends/list";
    }

    @RequestMapping("/update/{id}")
    public String updateJobs(@PathVariable("id") long id, Model model) {
        model.addAttribute("friend", friend.findById(id).get());
        return "/friends/add";
    }

    @RequestMapping("/delete/{id}")
    public String delJobs(@PathVariable("id") long id) {
        friend.deleteById(id);
        return "redirect:/";
    }
}