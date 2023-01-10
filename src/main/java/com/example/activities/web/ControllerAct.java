package com.example.activities.web;

import com.example.activities.model.Board;
import com.example.activities.model.User;
import com.example.activities.repositories.UserRepository;
import com.example.activities.services.BoardService;
import com.example.activities.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class ControllerAct {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserService userService;
    private final BoardService boardService;
    private Board newBoard = new Board();
    @Autowired
    private UserRepository userRepository;

    public ControllerAct(UserService userService, BoardService boardService){
        this.userService = userService;
        this.boardService = boardService;
    }

    @GetMapping("registration")
    //@ResponseBody
    public String registration(Model m){
        m.addAttribute("newUser", new User("","", newBoard, "user"));
        m.addAttribute(newBoard);
        return "login-screen";
    }

    @PostMapping("registration")
    public String registrationPost(Model m, @ModelAttribute User u, @ModelAttribute Board b){
        u.setBoard(b);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setRole("user");
        userService.addUser(u);
        return "redirect:/registration";
    }

    @GetMapping("login")
        public String login(){
            return "login";
    }

    @GetMapping("board")
    public String board(Model m){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        m.addAttribute("user",authentication.getName());
        return "test";
    }

}
