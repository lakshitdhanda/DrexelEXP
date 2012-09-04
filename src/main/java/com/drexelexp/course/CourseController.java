package com.drexelexp.course;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.drexelexp.Query;
import com.drexelexp.baseDAO.SearchableDAO;
import com.drexelexp.professor.Professor;

/**
 * Controller for the Course object
 * @author
 *
 */

@Controller
public class CourseController {
	private void addUsername(Model model){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.getName().equals("anonymousUser")) {
				model.addAttribute("username","");
			} else {
				model.addAttribute("username",authentication.getName());
			}
	}
	
	private SearchableDAO<Course> _courseDAO;
	private SearchableDAO<Course> getCourseDAO(){
		if(_courseDAO!=null)
			return _courseDAO;
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		_courseDAO = (JdbcCourseDAO) context.getBean("courseDAO");
		
		return _courseDAO;
	}
	
	@RequestMapping(value="/course", method = RequestMethod.GET)
	public String showAll(Model model) {
		addUsername(model);
		
		List<Course> courses = getCourseDAO().getAll();
		model.addAttribute("courses", courses);		
		
		return "course/list";
	}
	
	@RequestMapping(value="/course/show/{courseID}", method = RequestMethod.GET)
	public String show(@PathVariable String courseID, Model model) {
		addUsername(model);
		
		Course course = getCourseDAO().getById(Integer.parseInt(courseID));
		
		model.addAttribute("course",course);
		
		return "course/show";
	}
	
	@RequestMapping(value="/course/search", method = RequestMethod.GET)
	public ModelAndView search(Model model)
	{
		addUsername(model);
		
		return new ModelAndView("course/search", "command", new Query());
	}
	
	@RequestMapping(value="/course/search", method = RequestMethod.POST)
	public String search(@ModelAttribute("query") String query, Model model) {
		System.out.println(query);
		
		List<Course> courses = getCourseDAO().search(query);		
		
		model.addAttribute("courses",courses);
		return "course/list";
	}
}
