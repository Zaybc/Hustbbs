package com.Hustbbs.community.controller;

import org.springframework.web.bind.annotation.PostMapping;
import com.Hustbbs.community.dto.PaginationDTO;
import com.Hustbbs.community.model.User;
import com.Hustbbs.community.service.NotificationService;
import com.Hustbbs.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.Hustbbs.community.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("sectionName", "最新回复");
        } else if ("modify".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("sectionName", "最新回复");
            String userName = user.getName();
            String userBio = user.getBio();
            model.addAttribute("userName", userName);
            model.addAttribute("userBio", userBio);
            return "profile_modify";
        }
        return "profile";
    }

    @GetMapping("/profile/findByUser/{id}")
    public String profileFindByUser(
                          @PathVariable(name = "id") Long id,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        if (id == null){
            return "redirect:/";
        }
        model.addAttribute("section", "questions");
        model.addAttribute("sectionName", "Ta的提问");
        PaginationDTO paginationDTO = questionService.list(id, page, size);
        model.addAttribute("pagination", paginationDTO);

        return "profile";
    }

    @PostMapping("/profile")
    public String modifyProfile(HttpServletRequest request
            , @RequestParam(name = "bio", required = false) String bio,
                                @RequestParam(name = "userName", required = false) String userName) {
        User user = (User) request.getSession().getAttribute("user");
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setBio(bio);
        newUser.setName(userName);
        userService.modifyUser(newUser);
        return "redirect:/";
    }

}
