//package com.jobtracker.job_application_tracker.config;
//
//import com.jobtracker.job_application_tracker.model.Users;
//import com.jobtracker.job_application_tracker.repository.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataSeeder implements CommandLineRunner {
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if(usersRepository.findByUsername("admin").isEmpty()){
//            Users admin = new Users();
//            admin.setUsername("admin");
//
//            String hashedPassword = new BCryptPasswordEncoder().encode("admin123");
//            admin.setPassword(hashedPassword);
//            admin.setRole("ROLE_USER");
//            usersRepository.save(admin);
//            System.out.println("Admin user created: username='admin', password='admin123'");
//
//        }
//    }
//}
