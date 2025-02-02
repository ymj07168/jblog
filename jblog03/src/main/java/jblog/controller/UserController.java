package jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import jblog.service.BlogService;
import jblog.service.UserService;
import jblog.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	private BlogService blogService;
	
	public UserController(UserService userService, BlogService blogService) {
		this.userService = userService;
		this.blogService = blogService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("/join")
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(userVo);
		blogService.addBlog(userVo.getId());
		blogService.addDefaultCategory(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
}
