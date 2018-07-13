package com.example.demo.services;

import com.example.demo.model.AppUser;
import com.example.demo.model.AppUserRepository;
import com.example.demo.model.Friend;
import com.example.demo.model.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    private AppUser thisUser;

    @Autowired
    AppUserRepository users;

    @Autowired
    FriendRepository friends;

    public Iterable<Friend> getMyFriends(Authentication myDetails){
        return friends.findAllByMyFriend(users.findByUsername(myDetails.getName()));
    }

    public Iterable<Friend> rankMyFriends(Authentication myDetails){
        myDetails.getName();
        return friends.findAllByMyFriendOrderByMyRankDesc(users.findByUsername(myDetails.getName()));
    }

    //why is this a Friend type on Fi's example?
    public void save (Friend friend, Authentication authentication){
        thisUser = users.findByUsername(authentication.getName());
        friend.setMyFriend(thisUser);
        friends.save(friend);
    }
}
