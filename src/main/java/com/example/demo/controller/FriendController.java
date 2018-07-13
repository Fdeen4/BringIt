package com.example.demo.controller;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.config.CloudinaryConfig;
import com.example.demo.model.Friend;
import com.example.demo.model.FriendRepository;
import com.example.demo.model.UserRoleRepository;
import com.example.demo.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    FriendRepository friend;

   @Autowired
    FriendService friends;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    UserRoleRepository roles;

    @RequestMapping("/")
    public String showFriends(Model model, Authentication auth) {
        model.addAttribute("friends", friends.getMyFriends(auth));

        if (auth.getAuthorities().contains(roles.findByRole("ADMIN")))
            return "redirect:/friends/ranked";
        else
            return "/friends/list";
    }

    @RequestMapping("/add")
    public String addFriend(Model model) {
        model.addAttribute("friend", new Friend());
        return "/friends/add";
    }

    @RequestMapping("/save")
    public String saveFriend(@ModelAttribute("friend") Friend friend, Authentication myDetails, MultipartHttpServletRequest request) {
        MultipartFile f = request.getFile("file");
        if (f != null && !f.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(f.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                String uploadedName = (String) uploadResult.get("public_id");
                String transformedImage = cloudc.createUrl(uploadedName);
                friend.setImage(transformedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            friend.setImage("/img/defaultfriend.png");
        friends.save(friend, myDetails);

        return "redirect:/friends/";
    }

    @RequestMapping("/ranked")
    public String showRankedFriends(Model model, Authentication auth) {
        model.addAttribute("friends", friends.rankMyFriends(auth));
        return "/friends/list";
    }


}