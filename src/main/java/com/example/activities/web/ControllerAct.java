package com.example.activities.web;

import com.example.activities.model.Activity;
import com.example.activities.model.User;
import com.example.activities.repositories.ActivityRepository;
import com.example.activities.repositories.UserRepository;
import com.example.activities.services.ActivityService;
import com.example.activities.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerAct {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final ActivityService activityService;

    User userAct = new User();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;

    public ControllerAct(UserService userService, ActivityService activityService){
        this.userService = userService;
        this.activityService = activityService;
    }
    @GetMapping("registration")
    //@ResponseBody
    public String registration(Model m){
        m.addAttribute("newUser", new User("","", "user"));
        return "login-screen";
    }

    @PostMapping("registration")
    public String registrationPost(Model m, @ModelAttribute User u){
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setRole("user");
        userService.addUser(u);
        return "redirect:/login";
    }

    @GetMapping("login")
        public String login(){
            return "login";
    }

    @GetMapping("user-board")
    public String board(Model m){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());

        User pomUser = userRepository.findByUsername(authentication.getName());

        List<Activity> activitiesAll = activityRepository.findAll();
        List<Activity> activitiesNew = new ArrayList<>();
        List<Activity> activitiesToBeImp = new ArrayList<>();
        List<Activity> activitiesDoing = new ArrayList<>();
        List<Activity> activitiesDone = new ArrayList<>();

        for (int i = 0; i< activitiesAll.size();i++){
            if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("new")){
                activitiesNew.add(activitiesAll.get(i));
            }else if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("toBeImp")){
                activitiesToBeImp.add(activitiesAll.get(i));
            }else if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("doing")) {
                activitiesDoing.add(activitiesAll.get(i));
            }else if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("done")) {
                activitiesDone.add(activitiesAll.get(i));
            }
        }

        m.addAttribute("activitiesNew",activitiesNew);
        m.addAttribute("activitiesToBeImp",activitiesToBeImp);
        m.addAttribute("activitiesDoing",activitiesDoing);
        m.addAttribute("activitiesDone",activitiesDone);
        m.addAttribute("activitiesDone",activitiesDone);
        m.addAttribute("numOfActivitiesNew",activitiesNew.size());
        m.addAttribute("numOfActivitiesTBI",activitiesToBeImp.size());
        m.addAttribute("numOfActivitiesWO",activitiesDoing.size());
        m.addAttribute("numOfActivitiesDone",activitiesDone.size());
        return "user-board";
    }

    @GetMapping("activity")
    public String activity(Model m){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());
        List<User> listUser = userRepository.findAll();
        m.addAttribute("usersFind", listUser);
        m.addAttribute("newActivity", new Activity("", 0f, new Date(2023-01-11),
                new Date(2023-01-11),0f,"","", new User(),0));
        return "activity";
    }

    @PostMapping("activity")
    public String activityPost(Model m,  @ModelAttribute Activity a){

        a.setState("new");
        activityService.addActivity(a);
        return "redirect:/user-board";
    }

    @GetMapping("activity/{id}")
    public String updateAct(@PathVariable int id, Model m){
        Activity findActivity = activityService.findActivity(id);
        m.addAttribute("newActivity", new Activity(
                findActivity.getId(),
                findActivity.getName(),
                findActivity.getTime(),
                findActivity.getDateFrom(),
                findActivity.getDateTo(),
                findActivity.getPriority(),
                findActivity.getState(),
                findActivity.getTimeWorked(),
                findActivity.getDescription(),
                findActivity.getUser()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());
        List<User> listUser = userRepository.findAll();
        m.addAttribute("usersFind", listUser);


        return "activity-edit";
    }

    @PostMapping("activity/{id}")
    public String updateActPost(@PathVariable int id, Model m, @ModelAttribute Activity a){


        activityService.updateActivity(a);
        return "redirect:/user-board";
    }

}
