package com.example.activities.web;

import com.example.activities.exception.PassNotMatchingException;
import com.example.activities.model.*;
import com.example.activities.repositories.ActivityRepository;
import com.example.activities.repositories.CommentRepository;
import com.example.activities.repositories.UserRepository;
import com.example.activities.repositories.WorkRegisterRepository;
import com.example.activities.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

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

    @RequestMapping("/")
    public String index() {
        return "redirect:/user-board";
    }

    @GetMapping("registration")
    //@ResponseBody
    public String registration(Model m){
        m.addAttribute("newUser", new User("","", ""));
        return "login-screen";
    }

    @PostMapping("registration")
    public String registrationPost(Model m, @Valid @ModelAttribute User u, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        List<User> usersPom = userRepository.findAll();
        boolean pom = false;
        boolean pomValid = false;


        for(User usr : usersPom){
            if(usr.getUsername().equals(u.getUsername())){
                redirectAttributes.addFlashAttribute("messageUsr", "Username already exist");
                redirectAttributes.addFlashAttribute("messageUsrNotVisible", "notVis");
                pom = true;
            }
        }

        if(!isPassValid(u.getPassword())){
            redirectAttributes.addFlashAttribute("messageValid", "Password is not valid!");
            redirectAttributes.addFlashAttribute("messageValidNotVisible", "notVis");
            pomValid = true;
        }

            if (u.getPassword().equals(u.getRole()) && !pom && !pomValid){
                u.setPassword(passwordEncoder.encode(u.getPassword()));
                u.setRole("user");
                userService.addUser(u);
                redirectAttributes.addFlashAttribute("messageRegSucc", "Registration completed succesfull! You may log in!");
                redirectAttributes.addFlashAttribute("messageRegSuccNotVisible", "notVis");
                return "redirect:/login";
            }else if(!u.getPassword().equals(u.getRole())){
                redirectAttributes.addFlashAttribute("messagePass", "Passwords not matching");
                redirectAttributes.addFlashAttribute("messagePassNotVisible", "notVis");
            }
        return "redirect:/registration";
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

        m.addAttribute("userActual",pomUser);

        List<Activity> activitiesAll = activityRepository.findAllActivitiesDesc();
        List<Activity> activitiesNew = new ArrayList<>();
        List<Activity> activitiesToBeImp = new ArrayList<>();
        List<Activity> activitiesDoing = new ArrayList<>();
        List<Activity> activitiesDone = new ArrayList<>();
        List<Float> timePercNew = new ArrayList<>();


        if(pomUser.getRole().equals("user")){
            for (int i = 0; i< activitiesAll.size();i++){
                if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("new")){
                    if(activitiesAll.get(i).getTime() < activitiesAll.get(i).getTimeWorked()){
                        timePercNew.add(
                                activitiesAll.get(i).getTimeWorked() /
                                        (activitiesAll.get(i).getTime() / 100));
                    }else{
                        timePercNew.add(activitiesAll.get(i).getTime());
                    }
                    activitiesNew.add(activitiesAll.get(i));
                }else if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("toBeImp")){
                    activitiesToBeImp.add(activitiesAll.get(i));
                }else if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("doing")) {
                    activitiesDoing.add(activitiesAll.get(i));
                }else if(activitiesAll.get(i).getUser().getId() == pomUser.getId() && activitiesAll.get(i).getState().equals("done")) {
                    activitiesDone.add(activitiesAll.get(i));
                }
            }
        } else if (pomUser.getRole().equals("admin")) {
            for (int i = 0; i< activitiesAll.size();i++){
                if(activitiesAll.get(i).getState().equals("new")){
                    if(activitiesAll.get(i).getTime() < activitiesAll.get(i).getTimeWorked()){
                        timePercNew.add(
                                activitiesAll.get(i).getTimeWorked() /
                                        (activitiesAll.get(i).getTime() / 100));
                    }else{
                        timePercNew.add(activitiesAll.get(i).getTime());
                    }
                    activitiesNew.add(activitiesAll.get(i));
                }else if(activitiesAll.get(i).getState().equals("toBeImp")){
                    activitiesToBeImp.add(activitiesAll.get(i));
                }else if(activitiesAll.get(i).getState().equals("doing")) {
                    activitiesDoing.add(activitiesAll.get(i));
                }else if(activitiesAll.get(i).getState().equals("done")) {
                    activitiesDone.add(activitiesAll.get(i));
                }
            }
        }


        m.addAttribute("timePercNew",timePercNew);
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
        List<User> listWithoutAdmin = new ArrayList<>();

        for(User usr:listUser){
            if (usr.getRole().equals("user")){
                listWithoutAdmin.add(usr);
            }
        }

        m.addAttribute("usersFind", listWithoutAdmin);
        m.addAttribute("newActivity", new Activity("", 0f, new Date(02-22-22),
                new Date(2-22-2022),0f,"","", new User(),0));
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
        List<User> listWithoutAdmin = new ArrayList<>();

        for(User usr:listUser){
            if (usr.getRole().equals("user")){
                listWithoutAdmin.add(usr);
            }
        }

        m.addAttribute("usersFind", listWithoutAdmin);

        List<Activity> activitiesPom = activityRepository.findAll();
        List<Activity> childActivities = new ArrayList<>();

        for (int i =0; i<activitiesPom.size();i++){
            if(activitiesPom.get(i).getMotherActivity().getId() == id){
                childActivities.add(activitiesPom.get(i));
            }else{i++;}
        }

        List<Comment> commentsPom = commentRepository.findAllCommentsDesc();
        List<Comment> comments = new ArrayList<>();
        List<String> minutesFrom= new ArrayList<>();

        for (int i =0; i<commentsPom.size();i++){
            if(commentsPom.get(i).getActivity().getId() == id){
                comments.add(commentsPom.get(i));

                if(commentsPom.get(i).getTime().getMinute() < 10){
                    String minFormatted = String.format("%02d", commentsPom.get(i).getTime().getMinute());
                    minutesFrom.add(minFormatted);
                }else{
                    String minToString = Integer.toString(commentsPom.get(i).getTime().getMinute());
                    minutesFrom.add(minToString);
                }
            }else{i++;}
        }

        m.addAttribute("minutesFrom", minutesFrom);
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

        List<WorkRegister> pomRegister = workRegisterRepository.findAllReportsDesc();
        List<Float> pomTime = new ArrayList<>();
        List<String> minutesFrom= new ArrayList<>();
        List<String> minutesTo= new ArrayList<>();

        for(int i =0; i < pomRegister.size();i++){

            if (pomUser.getRole().equals("user")){
                if(pomRegister.get(i).getUser() == pomUser){
                    finalReg.add(pomRegister.get(i));
                    if(pomRegister.get(i).getTimeFrom().getMinute() < 10){
                        String minFormatted = String.format("%02d", pomRegister.get(i).getTimeFrom().getMinute());
                        minutesFrom.add(minFormatted);
                    }else if(pomRegister.get(i).getTimeFrom().getMinute() >= 10){
                        String minToString = Integer.toString(pomRegister.get(i).getTimeFrom().getMinute());
                        minutesFrom.add(minToString);
                    }
                    if(pomRegister.get(i).getTimeTo().getMinute() < 10){
                        String minFormattedTo = String.format("%02d", pomRegister.get(i).getTimeTo().getMinute());
                        minutesTo.add(minFormattedTo);
                    }else if(pomRegister.get(i).getTimeTo().getMinute() >= 10){
                        String minToString = Integer.toString(pomRegister.get(i).getTimeTo().getMinute());
                        minutesTo.add(minToString);
                    }
                }
            } else if (pomUser.getRole().equals("admin")) {
                    finalReg.add(pomRegister.get(i));
                    if(pomRegister.get(i).getTimeFrom().getMinute() < 10){
                        String minFormatted = String.format("%02d", pomRegister.get(i).getTimeFrom().getMinute());
                        minutesFrom.add(minFormatted);
                    }else if(pomRegister.get(i).getTimeFrom().getMinute() >= 10){
                        String minToString = Integer.toString(pomRegister.get(i).getTimeFrom().getMinute());
                        minutesFrom.add(minToString);
                    }
                    if(pomRegister.get(i).getTimeTo().getMinute() < 10){
                        String minFormattedTo = String.format("%02d", pomRegister.get(i).getTimeTo().getMinute());
                        minutesTo.add(minFormattedTo);
                    }else if(pomRegister.get(i).getTimeTo().getMinute() >= 10){
                        String minToString = Integer.toString(pomRegister.get(i).getTimeTo().getMinute());
                        minutesTo.add(minToString);
                    }

            }
        }

        List<User> listUser = userRepository.findAll();
        List<User> listWithoutAdmin = new ArrayList<>();

        for(User usr:listUser){
            if (usr.getRole().equals("user")){
                listWithoutAdmin.add(usr);
            }
        }

        m.addAttribute("usersFind", listWithoutAdmin);
        m.addAttribute("actTest",new Activity());
        m.addAttribute("minutesTo", minutesTo);
        m.addAttribute("minutesFrom", minutesFrom);
        m.addAttribute("user",authentication.getName());
        m.addAttribute("userActual",pomUser);
        m.addAttribute("reports",finalReg);
        return "workReports";
    }

    @PostMapping("workReports")
    public String showReportsPost(Model m, @ModelAttribute Activity a){

        return "redirect:/workReports/"+a.getUser().getId();
    }

    @GetMapping("workReports/{id_mother}")
    public String showReportsUser(Model m, @PathVariable int id_mother){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User pomUserSel = userRepository.findByUsername(authentication.getName());

        User pomUser = userService.findUser(id_mother);

        List<WorkRegister> finalReg = new ArrayList<>();

        List<WorkRegister> pomRegister = workRegisterRepository.findAllReportsDesc();
        List<Float> pomTime = new ArrayList<>();
        List<String> minutesFrom= new ArrayList<>();
        List<String> minutesTo= new ArrayList<>();

        for(int i =0; i < pomRegister.size();i++){
                if(pomRegister.get(i).getUser() == pomUser){
                    finalReg.add(pomRegister.get(i));
                    if(pomRegister.get(i).getTimeFrom().getMinute() < 10){
                        String minFormatted = String.format("%02d", pomRegister.get(i).getTimeFrom().getMinute());
                        minutesFrom.add(minFormatted);
                    }else if(pomRegister.get(i).getTimeFrom().getMinute() >= 10){
                        String minToString = Integer.toString(pomRegister.get(i).getTimeFrom().getMinute());
                        minutesFrom.add(minToString);
                    }
                    if(pomRegister.get(i).getTimeTo().getMinute() < 10){
                        String minFormattedTo = String.format("%02d", pomRegister.get(i).getTimeTo().getMinute());
                        minutesTo.add(minFormattedTo);
                    }else if(pomRegister.get(i).getTimeTo().getMinute() >= 10){
                        String minToString = Integer.toString(pomRegister.get(i).getTimeTo().getMinute());
                        minutesTo.add(minToString);
                    }
                }

        }
        List<User> listUser = userRepository.findAll();
        List<User> listWithoutAdmin = new ArrayList<>();

        for(User usr:listUser){
            if (usr.getRole().equals("user")){
                listWithoutAdmin.add(usr);
            }
        }

        m.addAttribute("actTest",new Activity());
        m.addAttribute("usersFind", listWithoutAdmin);
        m.addAttribute("minutesTo", minutesTo);
        m.addAttribute("minutesFrom", minutesFrom);
        m.addAttribute("user",pomUser.getUsername());
        m.addAttribute("userActual",pomUserSel);
        m.addAttribute("reports",finalReg);
        return "workReports";
    }

    @PostMapping("workReports//{id_mother}")
    public String showReportsUserPost(Model m, @ModelAttribute Activity a){

        return "redirect:/workReports/"+a.getUser().getId();
    }

    @GetMapping("activity/{id_mother}/createChildActivity")
    public String childActivity(Model m, @PathVariable int id_mother){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());
        List<User> listUser = userRepository.findAll();
        Activity activityPom = activityService.findActivity(id_mother);
        List<User> listWithoutAdmin = new ArrayList<>();

        for(User usr:listUser){
            if (usr.getRole().equals("user")){
                listWithoutAdmin.add(usr);
            }
        }

        m.addAttribute("actualActivity", activityPom);
        m.addAttribute("usersFind", listWithoutAdmin);
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

        Activity activityPom = activityService.findActivity(id_mother);
        m.addAttribute("actualActivity", activityPom);
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

    @GetMapping({ "activity/{id_mother}/document.xlsx" })
    public ActivityXlsxView createXlsxActivityList(Model m, @PathVariable int id_mother) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for JSon/Object mapping
        WorkRegisters workRegisters = new WorkRegisters();
        Activity activityPom = new Activity();
        activityPom = activityService.findActivity(id_mother);
        workRegisters.getRegisterList().addAll(this.workRegisterRepository.findAllReportsDesc());
        return new ActivityXlsxView(activityPom,workRegisters);
    }

    @GetMapping("user-edit")
    public String editUser(Model m){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());

        m.addAttribute("userEdit",new User());
        return "userEdit";
    }

    @PostMapping("user-edit")
    public String editUserPost(Model m,  @ModelAttribute User u, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User myUser = userRepository.findByUsername(authentication.getName());
        u.setId(myUser.getId());
        u.setUsername(myUser.getUsername());
        u.setActivities(myUser.getActivities());
        u.setComments(myUser.getComments());
        u.setWorkRegisters(myUser.getWorkRegisters());

        boolean pomValid = false;

        if(!isPassValid(u.getPassword())){
            redirectAttributes.addFlashAttribute("messageValid", "Password is not valid!");
            redirectAttributes.addFlashAttribute("messageValidNotVisible", "notVis");
            pomValid = true;
        }

        if (u.getPassword().equals(u.getRole()) && !pomValid){
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            u.setRole("user");
            System.out.println(u.getId());
            userService.updateUser(u);
            redirectAttributes.addFlashAttribute("messageRegSucc", "Registration completed succesfull! You may log in!");
            redirectAttributes.addFlashAttribute("messageRegSuccNotVisible", "notVis");
            return "redirect:/user-board";
        }else if(!u.getPassword().equals(u.getRole())){
            redirectAttributes.addFlashAttribute("messagePass", "Passwords not matching");
            redirectAttributes.addFlashAttribute("messagePassNotVisible", "notVis");
        }


        return "redirect:/user-edit";
    }

    public static boolean isPassValid(String password)
    {
        if(password.matches("(?!.*[^A-Za-z0-9])(?=.{6,}).*\\d.*\\d.*")) {
            System.out.println("Password is valid");
            return true;
        }else {
            return false;
        }

    }
}
