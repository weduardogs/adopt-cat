package com.adoptacat.controllers;

import com.adoptacat.model.Gato;
import com.adoptacat.model.Role;
import com.adoptacat.model.User;
import com.adoptacat.service.RoleService;
import com.adoptacat.service.UserService;
import com.adoptacat.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.*;


@Controller
@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserValidator userValidator;


    @RequestMapping("/showUsers")
    public String showUsers(Model model) {

        List<HashMap<String, String>> users = userService.findAll();

        model.addAttribute("users", users);

        return "users-list";
    }

    @GetMapping("/createUser")
    public String createUser(Model model) {
        List<Role> roles = roleService.findAll();

        User user = new User();
        user.setRolSelected("APPLY");
        model.addAttribute("user", user);
        model.addAttribute("rolesOptions", roles);

        return "user-form";
    }

    @PostMapping("/saveUser")
    public String registration(@Valid User user, BindingResult bindingResult, Model model,
                               RedirectAttributes flash, SessionStatus status) {

        String returnPage = user.getId() != null ? "redirect:/showUsers" : "redirect:/login";

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()) {
            return "user-form";
        }

        Set<Role> roles = new HashSet<>();
        Role role = roleService.findByName(user.getRolSelected());
        roles.add(role);
        user.setRoles(roles);

        userService.save(user);

        flash.addFlashAttribute("success", "Usuario guardado con éxito");

        return returnPage;
    }

    @GetMapping("/editUser/{userName}")
    public String editUser(@PathVariable(value = "userName") String userName,
                           Model model,
                           @RequestParam(value = "error", required = false) String error,
                           RedirectAttributes flash) {

        User user = userService.findByUsername(userName);
        List<Role> roles = roleService.findAll();

        user.setRolSelected(rolesForCurrentUser(user).get(0));

        if(userName.length() <= 0) {
            flash.addFlashAttribute("error", "El usuario no existe en la base de datos");
            return "redirect:/showUsers";
        }

        model.addAttribute("user", user);
        model.addAttribute("rolesOptions", roles);

        return "user-form";
    }


    @RequestMapping("/deleteUser/{userName}")
    public String deleteGato(@PathVariable(value = "userName") String userName, RedirectAttributes flash) {

        userService.deleteUserByUserName(userName);

        if (userName.length() > 0) {
            userService.deleteUserByUserName(userName);
            flash.addFlashAttribute("success", "Usuario eliminado con éxito eliminado con éxito");
        }

        return "redirect:/showUsers";
    }


    public List<String> rolesForCurrentUser(User user) {
        List<String> roles = new ArrayList<>();

        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }

        return roles;
    }


}
