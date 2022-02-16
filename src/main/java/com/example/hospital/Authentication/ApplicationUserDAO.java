package com.example.hospital.Authentication;

import java.util.Optional;

public interface ApplicationUserDAO {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username); //username = email, but now 'name' from fake class
}
