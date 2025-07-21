package com.example.scripto.implementation;

import com.example.scripto.dto.UserDto;
import com.example.scripto.dto.UserUpdateDto;
import com.example.scripto.entity.User;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.service.IUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUser {

    @Autowired
    private UserRepository userRepository;


    // create new user
    @Override
    public ResponseEntity<User> createNewUser(UserDto userDto) {
       try {
           User user = new User();
           user.setFullName(userDto.getFullName()); // ✅ Set full name
           user.setEmail(userDto.getEmail());
           user.setPassword(userDto.getPassword());
           user.setRole(userDto.getRole().toUpperCase());
           user.setMobileNo(userDto.getMobileNo());

           if ("SELLER".equalsIgnoreCase(userDto.getRole())) {
               user.setShopName(userDto.getShopName());
           } else {
               user.setShopName(null); // Ensure BUYER cannot set shop name
           }

           return new ResponseEntity<>(userRepository.save(user),HttpStatus.CREATED);
       }
       catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }





    //for update the user password
    @Override
    public ResponseEntity<User> updateUser(String email, UserUpdateDto updateDto) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new RuntimeException("User Not Exist !!");
            }

            // ✅ Update full name if provided
            if (updateDto.getFullName() != null && !updateDto.getFullName().isBlank()) {
                user.setFullName(updateDto.getFullName());
            }

            // ✅ Update password if provided
            if (updateDto.getPassword() != null && !updateDto.getPassword().isBlank()) {
                user.setPassword(updateDto.getPassword());
            }

            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // for delete the user by email
    @Override
    @Transactional
    public ResponseEntity<String> deleteUser(String email) {
       try {
           User user = userRepository.findByEmail(email);
           if(user == null){
               throw new RuntimeException("User Not Present !!");
           }

           userRepository.deleteUserByEmail(email);

           return new ResponseEntity<>("Deleted -_-",HttpStatus.OK);
       } catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>("Something Went Wrong !!",HttpStatus.BAD_REQUEST);
       }
    }




    // for getting all user
    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> allUser = userRepository.findAll();
            return new ResponseEntity<>(allUser,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }

}



