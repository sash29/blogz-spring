package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		//get parameters from request
		
		String usrName = request.getParameter("username");
		String pswd = request.getParameter("password");
		String cnfirmPswd = request.getParameter("verify");
		
		User user1 = new User();
		boolean validUser = User.isValidUsername(usrName);
   		boolean validPswd =  User. isValidPassword(pswd);
  		//boolean matchingPswd = user1.isMatchingPassword(pswd);
		
		//validate parameters(username, password, verify)
		//if they validate, create new user, & put them in the session(use function frm AbstractController)
			if(validUser && validPswd && matchingPswd)
				{User newUser = new User(usrName,pswd);
				model.addAttribute("userName", usrName);
				model.addAttribute("pswd",pswd);
				userDao.save(newUser);
				//Session thisSession = request.getSession();
				setUserInSession (request.getSession(),newUser);
				}
		
		return "redirect:blog/newpost";
		
		//Session thisSession = request.getSession
	
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
