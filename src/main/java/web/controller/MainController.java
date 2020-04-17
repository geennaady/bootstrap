package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@ComponentScan("web")
@RequestMapping("/")
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@GetMapping(value = "adminx")
	public String printX() {
		return "adminx";
	}

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC-SECURITY application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);

		return "hello";
	}

    @GetMapping(value = "login")
    public String loginPage(@AuthenticationPrincipal User user) {
		return "login";
    }

	@GetMapping(value = "registration")
	public String registration(ModelMap model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping(value = "registration")
	public String addUser(@ModelAttribute("userForm") User user, BindingResult bindingResult, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		try {
			user.setRoles(roleService.getAuthorityById(2L));
			userService.addUser(user);
		} catch (Exception e) {
			model.addAttribute("message", "That name is already own");
			return "error";
		}

		return "redirect:/";
	}

	@GetMapping(value = "user")
	public String userPage(@AuthenticationPrincipal User user, ModelMap model) {
		model.addAttribute("authUser", user);
		return "user";
	}
}