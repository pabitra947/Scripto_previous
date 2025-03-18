package com.example.scripto.service;

import com.example.scripto.model.User;
import com.example.scripto.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userrepo;


    // get all the data of user using Email
    public Optional<User> findByEmail(String email) {
        return userrepo.findByEmail(email);
    }


    //    get user by id
    public Optional<User> findById(UUID id)
    {
        return userrepo.findById(id);
    }



    //for new user
    public User savedata(User user)
    {
        return userrepo.save(user);
    }


    //delete the user details
    public void deleteuser(UUID uuid)
    {
        userrepo.deleteById(uuid);
    }

}
