package com.license.ProjectSocialNetwork.services;

import com.license.ProjectSocialNetwork.model.User;
import com.license.ProjectSocialNetwork.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service //("userService")
//@Transactional
public class UserServiceImpl implements UserService{
    @PersistenceContext
    private EntityManager em;

    private final static Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
//        log.traceEntry("Returning all users...");
//        List<User> users = new LinkedList<>();
//        try {
//            users = userRepository.findAll();
//            log.trace("Returned all users");
//        }
//        catch (Exception ex){
//            log.error(ex);
//            System.out.println("Error Service "+ex);
//            return null;
//        }
//        log.traceExit();
        //return users;
//
//        System.out.println("BEGIN FIND ALL SERVICE");
//
//        userRepository.findAll().forEach(user -> {
//            System.out.printf("user");
//        });
//
//        System.out.println("END FIND ALL SERVICE");

        log.traceEntry("Returning all users...");
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        log.traceEntry("Saving user...");
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.traceEntry("Deleting user...");
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        log.traceEntry("Updating user...");
        userRepository.findById(user.getId()).ifPresent(u -> {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setGender(user.getGender());
            u.setUsername(user.getUsername());
            u.setPassword(user.getPassword());
        });
    }
}
