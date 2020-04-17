package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String printWelcome(@AuthenticationPrincipal User user, ModelMap model) {
        List<User> users = userService.listUsers();
        User empty = new User();

        model.addAttribute("empty", empty);
        model.addAttribute("admin", user);
        model.addAttribute("users", users);

        return "list";
    }

    @RequestMapping(value = "admin/add", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute User user, @RequestParam Long role) {
        ModelAndView model;

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getFirstName().isEmpty()
            || user.getLastName().isEmpty()) {
            model = new ModelAndView("redirect:/admin/error");
            model.addObject("message", "Enter the name or pass");
        } else {
            try {
                user.setRoles(roleService.getAuthorityById(role));
                user.setPassword(bCryptEncoder.encode(user.getPassword()));
                userService.addUser(user);
                model = new ModelAndView("redirect:/admin");
            } catch (Exception e) {
                model = new ModelAndView("redirect:/admin/error");
                model.addObject("message", "User already exists");
            }
        }

        return model;
    }

    @RequestMapping(value = "admin/error", method = RequestMethod.GET)
    public String errorHandler(@RequestParam String message, ModelMap model) {
        model.addAttribute("message", message);

        return "error";
    }

    @RequestMapping(value = "admin/delete", method = RequestMethod.POST)
    public ModelAndView deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);

        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "admin/update", method = RequestMethod.GET)
    public String showUpdate(ModelMap model) {
        return "update";
    }

    @RequestMapping(value = "admin/update", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute User user, @RequestParam Long role) {
        ModelAndView model;
        User oldUser = (User) userService.getUserById(user.getId());

        if(user.getFirstName().isEmpty()) {
            user.setFirstName(oldUser.getFirstName());
        }

        if(user.getLastName().isEmpty()) {
            user.setLastName(oldUser.getLastName());
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
            model = new ModelAndView("redirect:/admin/error");
            model.addObject("message", "Name already exists");
        }

        return model;
    }
}
