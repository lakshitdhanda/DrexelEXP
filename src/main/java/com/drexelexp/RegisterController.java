package com.drexelexp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.drexelexp.baseDAO.BaseDAO;
import com.drexelexp.user.JdbcUserDAO;
import com.drexelexp.user.User;

@Controller
public class RegisterController {
	
	@RequestMapping(value="/create_user", method = RequestMethod.POST)
	public String create_user(@ModelAttribute("user")
    User user,ModelMap model) {
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("Spring-Module.xml");
		BaseDAO<User> userDAO = (JdbcUserDAO) context.getBean("userDAO");
		userDAO.insert(user);
		return "login";
	}
	
	@RequestMapping(value="/register")
	public ModelAndView showUsers() {
		return new ModelAndView("register", "command", new User());
	}
}
