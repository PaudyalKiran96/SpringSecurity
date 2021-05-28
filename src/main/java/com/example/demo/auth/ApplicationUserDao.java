package com.example.demo.auth;


import java.util.Optional;

public interface ApplicationUserDao {               //DAO = Data Access Object, i.e interface

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);

}
