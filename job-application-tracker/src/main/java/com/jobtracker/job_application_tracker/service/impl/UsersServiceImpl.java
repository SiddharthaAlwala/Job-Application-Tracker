package com.jobtracker.job_application_tracker.service.impl;

import com.jobtracker.job_application_tracker.model.Users;
import com.jobtracker.job_application_tracker.repository.UsersRepository;
import com.jobtracker.job_application_tracker.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12) ;
    @Autowired
    UsersRepository usersRepository;

    @Override
    public void register(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));

        usersRepository.save(users);
    }
}
