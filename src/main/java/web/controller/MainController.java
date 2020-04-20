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
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@ComponentScan("web")
@RequestMapping("/")
public class MainController {
	@GetMapping(value = "/")
	public ModelAndView printWelcome() {
		return new ModelAndView("redirect:/login");
	}

    @GetMapping(value = "login")
    public String loginPage() {
		return "login";
    }

    @GetMapping(value = "user")
	public String userPage(@AuthenticationPrincipal User user, ModelMap model) {
		model.addAttribute("authUser", user);
		return "user";
	}
}