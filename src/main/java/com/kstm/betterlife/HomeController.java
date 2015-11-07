package com.kstm.betterlife;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kstm.betterlife.domain.Contact;
import com.kstm.betterlife.domain.User;
import com.kstm.betterlife.form.LoginForm;
import com.kstm.betterlife.service.ContactService;
import com.kstm.betterlife.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@Autowired
	private UserService userService;
	@Autowired
	private ContactService contactService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {


		User user = (User) session.getAttribute("user");
		if (user==null){
			return new ModelAndView("redirect:/");
		}
		model.addAttribute("user", user);
		
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		List<Contact> contacts = contactService.listContact();
		model.addAttribute("contacts", contacts);
		
		Long countContact=contactService.countContact();
		model.addAttribute("countContact", countContact);
		
		return new ModelAndView("home");
	}

	@RequestMapping(value = " /")
	public String loginPage() {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "login";
		} else {
			return "redirect:/home";
		}
	}

	@RequestMapping(value = "/loginCheck")
	public ModelAndView loginCheck(HttpServletRequest request, Locale locale, Model model, LoginForm loginForm) {
		boolean isValidUser = userService.hasMatchUser(loginForm.getUserName(), loginForm.getPassword());
		if (!isValidUser) {
			return new ModelAndView("login", "error", "用户名或密码错误。");
		} else {
			User user = userService.findUserByUserName(loginForm.getUserName());
			user.setLastIp(request.getRemoteAddr());
			user.setLastVisit(new Date());
			userService.loginSuccess(user);
			session.setAttribute("user", user);
			return new ModelAndView("redirect:/home");
		}
	}

	@RequestMapping(value = " /logout")
	public String logout() {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
}
