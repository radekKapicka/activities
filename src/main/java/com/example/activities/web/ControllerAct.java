package com.example.activities.web;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;
import com.example.activities.model.User;
import com.example.activities.model.WorkRegister;
import com.example.activities.repositories.ActivityRepository;
import com.example.activities.repositories.CommentRepository;
import com.example.activities.repositories.UserRepository;
import com.example.activities.repositories.WorkRegisterRepository;
import com.example.activities.services.ActivityService;
import com.example.activities.services.CommentService;
import com.example.activities.services.UserService;
import com.example.activities.services.WorkRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerAct {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final CommentService commentService;

    private final ActivityService activityService;

    private final WorkRegisterService workRegisterService;

    User userAct = new User();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private WorkRegisterRepository workRegisterRepository;

    public ControllerAct(UserService userService, CommentService commentService, ActivityService activityService, WorkRegisterService workRegisterService){
        this.userService = userService;
        this.commentService = commentService;
        this.activityService = activityService;
        this.workRegisterService = workRegisterService;
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
        a.setMotherActivity(a);
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
                findActivity.getUser(),
                findActivity.getMotherActivity()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());
        List<User> listUser = userRepository.findAll();
        m.addAttribute("usersFind", listUser);

        List<Activity> activitiesPom = activityRepository.findAll();
        List<Activity> childActivities = new ArrayList<>();

        for (int i =0; i<activitiesPom.size();i++){
            if(activitiesPom.get(i).getMotherActivity().getId() == id){
                childActivities.add(activitiesPom.get(i));
            }else{i++;}
        }

        List<Comment> commentsPom = commentRepository.findAll();
        List<Comment> comments = new ArrayList<>();

        for (int i =0; i<commentsPom.size();i++){
            if(commentsPom.get(i).getActivity().getId() == id){
                comments.add(commentsPom.get(i));
            }else{i++;}
        }

        m.addAttribute("comments", comments);
        m.addAttribute("childActivities", childActivities);

        return "activity-edit";
    }

    @PostMapping("activity/{id}")
    public String updateActPost(@PathVariable int id, Model m, @ModelAttribute Activity a){
        Activity findActivity = activityService.findActivity(id);
        a.setMotherActivity(findActivity.getMotherActivity());
        activityService.updateActivity(a);
        return "redirect:/user-board";
    }

    @GetMapping("activity/{id}/addReport")
    public String addReport(@PathVariable int id, Model m){

        Activity pom = activityService.findActivity(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());

        m.addAttribute("newReport", new WorkRegister(new User(),pom,LocalDateTime.of(1,1,1,1,1),LocalDateTime.of(1,1,1,1,1),0));

        return "addReport";
    }

    @PostMapping("activity/{id}/addReport")
    public String addReportPost(@PathVariable int id, Model m, @ModelAttribute WorkRegister w){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User pomUser = userRepository.findByUsername(authentication.getName());
        Activity pomActivity = activityService.findActivity(id);

        float hourFrom = (w.getTimeFrom().getHour()  * 60) + w.getTimeFrom().getMinute();
        float hourTo = (w.getTimeTo().getHour() * 60) + w.getTimeTo().getMinute();

        float dif = (hourTo - hourFrom) / 60;


        w.setPartOfTime(dif);
        w.setUser(pomUser);
        w.setActivity(pomActivity);
        pomActivity.setTimeWorked(pomActivity.getTimeWorked() + dif);
        activityService.updateActivity(pomActivity);
        workRegisterService.addRegister(w);
        return "redirect:/activity/{id}";
    }

    @GetMapping("workReports")
    public String showReports(Model m){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User pomUser = userRepository.findByUsername(authentication.getName());

        List<WorkRegister> finalReg = new ArrayList<>();

        List<WorkRegister> pomRegister = workRegisterRepository.findAll();
        List<Float> pomTime = new ArrayList<>();

        for(int i =0; i < pomRegister.size();i++){
            if(pomRegister.get(i).getUser() == pomUser){
                finalReg.add(pomRegister.get(i));

            }
        }

        m.addAttribute("user",authentication.getName());
        m.addAttribute("reports",finalReg);
        return "workReports";
    }

    @GetMapping("activity/{id_mother}/createChildActivity")
    public String childActivity(Model m){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());
        List<User> listUser = userRepository.findAll();
        m.addAttribute("usersFind", listUser);
        m.addAttribute("newActivityChild", new Activity(0,"", 0f, new Date(2023-01-11),
                new Date(2023-01-11),0f,"",0f, "",new User()));
        return "createChildActivity";
    }

    @PostMapping("activity/{id_mother}/createChildActivity")
    public String childActivityPost(Model m, @PathVariable int id_mother,  @ModelAttribute Activity a){
        Activity activityPom = activityService.findActivity(id_mother);
        System.out.println(a.getId());
        System.out.println(activityPom.getId());
        a.setMotherActivity(activityPom);
        activityPom.getChildActivities().add(a);

        a.setState("new");
        activityService.addActivity(a);
        return "redirect:/user-board";
    }

    @GetMapping("activity/{id_mother}/addComment")
    public String addComment(Model m, @PathVariable int id_mother){

        m.addAttribute("newComment", new Comment(new User(),new Activity(),LocalDateTime.now(),""));
        return "addComent";
    }

    @PostMapping("activity/{id_mother}/addComment")
    public String addCommentPost(Model m, @PathVariable int id_mother,  @ModelAttribute Comment c){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userPom = userRepository.findByUsername(authentication.getName());
        Activity activityPom = activityService.findActivity(id_mother);

        c.setActivity(activityPom);
        c.setUser(userPom);
        c.setTime(LocalDateTime.now());
        commentService.addComment(c);
        return "redirect:/activity/{id_mother}";
    }

}
