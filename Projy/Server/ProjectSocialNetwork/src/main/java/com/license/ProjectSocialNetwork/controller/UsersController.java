package com.license.ProjectSocialNetwork.controller;

import com.license.ProjectSocialNetwork.controller.dto.UserDTO;
import com.license.ProjectSocialNetwork.converter.UserConverter;
import com.license.ProjectSocialNetwork.model.User;
import com.license.ProjectSocialNetwork.services.UserService;
import com.license.ProjectSocialNetwork.services.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Controller
//@RequestMapping( value = "/" )
public class UsersController {

    private final static Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

//    @MessageMapping("/hello")
//    @SendTo("/topic/message")
//    public List<UserDTO> greeting(){
//        log.info("greeting(): Called...");
//
//        List<UserDTO> users = userConverter.convertModelsToDTOsList(userService.findAllUsers());
//        log.info("sending " + users);
//
//        simpMessagingTemplate.convertAndSend("/topic/message", users);
//        return users;
//    }

//    @RequestMapping("socket")
//    public @ResponseBody
//    String getWebSocket() {
//        log.info("getWebSocket(): Called...");
//        return "ws-broadcast";
//    }

    @GetMapping("users")
    public @ResponseBody
    List<UserDTO> getUsers() {
        List<User> users = new LinkedList<>();
        try {
            users = userService.findAllUsers();
            log.info(users.toString() + " returned");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return userConverter.convertModelsToDTOsList(users);
    }

    @PostMapping("user/add")
    public @ResponseBody
    UserDTO addUser(@RequestParam(value = "username") String username,
                    @RequestParam(value = "password") String password,
                    @RequestParam(value = "firstName") String firstName,
                    @RequestParam(value = "lastName") String lastName,
                    @RequestParam(value = "gender") String gender){
        log.info("addUser(): Called...");

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .gender(gender)
                .build();

        userService.saveUser(user);

        //System.out.println("User: " + user);

        UserDTO userDTO = userConverter.convertModelToDto(user);
        simpMessagingTemplate.convertAndSend("/topic/add", userDTO);

        log.info("added: " + userDTO);

        return userDTO;
    }


    @PutMapping("user/update")
    public @ResponseBody
    UserDTO updateUser (@RequestParam(value = "id") Long id,
                                          @RequestParam(value = "username") String username,
                                          @RequestParam(value = "password") String password,
                                          @RequestParam(value = "firstName") String firstName,
                                          @RequestParam(value = "lastName") String lastName,
                                          @RequestParam(value = "gender") String gender) {
        log.info("updateUser(): Called... with: " );

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .gender(gender)
                .build();
        user.setId(id);

        userService.updateUser(user);

        UserDTO userDTO = userConverter.convertModelToDto(user);
        simpMessagingTemplate.convertAndSend("/topic/update", userDTO);

        log.info("updated: " + userDTO);

        return userConverter.convertModelToDto(user);
    }

    @DeleteMapping("user/delete")
    public @ResponseBody
    Long deleteUser(@RequestParam(value = "id") Long id) {
        log.info("deleteUser(): Called...");

        userService.deleteUser(id);

        System.out.println("Userid: " + id);

        simpMessagingTemplate.convertAndSend("/topic/delete", id);

        log.info("deleted: " + id);

        return id;
    }
}
