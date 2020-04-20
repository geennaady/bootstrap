package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @GetMapping(value = "admin")
    public String printWelcome(@RequestParam(required = false) String message, @AuthenticationPrincipal User admin, ModelMap model) {
        List<User> users = userService.listUsers();

        model.addAttribute("admin", admin);
        model.addAttribute("user", new User());
        model.addAttribute("users", users);
        model.addAttribute("message", message);

        return "admin";
    }

    @PostMapping(value = "admin/add")
    public ModelAndView saveUser(@ModelAttribute User user, @RequestParam Long role) {
        ModelAndView model;


        if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getFirstName().isEmpty()
            || user.getLastName().isEmpty() || user.getAge() == null) {
            model = new ModelAndView("redirect:/admin");
            model.addObject("message", "All fields are required!");
        } else {
            try {
                user.setRoles(roleService.getAuthorityById(role));
                user.setPassword(bCryptEncoder.encode(user.getPassword()));
                userService.addUser(user);
                model = new ModelAndView("redirect:/admin");
            } catch (Exception e) {
                model = new ModelAndView("redirect:/admin");
                model.addObject("message", "Email already exists");
            }
        }

        return model;
    }

    @PostMapping(value = "admin/delete")
    public ModelAndView deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);

        return new ModelAndView("redirect:/admin");
    }

    @PostMapping(value = "admin/update")
    public ModelAndView updateUser(@Valid User user, @RequestParam Long role) {
        ModelAndView model;

        User oldUser = (User) userService.getUserById(user.getId());

        if(user.getFirstName().isEmpty()) {
            user.setFirstName(oldUser.getFirstName());
        }

        if(user.getLastName().isEmpty()) {
            user.setLastName(oldUser.getLastName());
        }

        if(user.getAge() == null) {
            user.setAge(oldUser.getAge());
        }

        if (user.getEmail().isEmpty()) {
            user.setEmail(oldUser.getEmail());
        }

        if(user.getPassword().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(bCryptEncoder.encode(user.getPassword()));
        }

        try {
            user.setRoles(roleService.getAuthorityById(role));
            userService.updateUser(user);
            model = new ModelAndView("redirect:/admin");
        } catch (Exception e) {
            model = new ModelAndView("redirect:/admin");
            model.addObject("message", "Email already exists");
        }

        return model;
    }
}
