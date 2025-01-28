package com.Job.Application.Service;

import com.Job.Application.Model.Users;
import com.Job.Application.Repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private CompanyRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = repo.findbyusername(username);

        if (users == null)
            throw new UsernameNotFoundException("User Not Found");

        return new MyUserDetails(users);
    }
}
