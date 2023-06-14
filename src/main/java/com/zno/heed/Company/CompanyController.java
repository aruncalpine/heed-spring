package com.zno.heed.Company;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zno.heed.MysqlEntites.Company;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.CompanyRepository;
import com.zno.heed.user.UserService;

@Controller
public class CompanyController {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyRepository companyRepository;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView getUser() {
		System.out.println("hiiiiiiiiiiiiiii");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());
		modelAndView.addObject("company", companyRepository.findAll());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getFullName());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("company/view");
	
		return modelAndView;
	}

	@RequestMapping("/company/create")
	public ModelAndView create() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getFullName());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("company/create");
		return modelAndView;
	}

	@RequestMapping("/company/save")
	public String save(@RequestParam String name, @RequestParam String address) {
		Company company = new Company(name, address);
		companyRepository.save(company);
		return "redirect:/company/show/" + company.getId();
	}

	@RequestMapping("/company/show/{id}")
	public ModelAndView show(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getFullName());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.addObject("company", companyRepository.findById(id).orElse(null));
		modelAndView.setViewName("company/show");
		return modelAndView;
	}

	@RequestMapping("/company/delete")
	public String delete(@RequestParam Long id) {
		Company company = companyRepository.findById(id).orElse(null);
		companyRepository.delete(company);

		return "redirect:/company";
	}

	@RequestMapping("/company/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getFullName());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.addObject("company", companyRepository.findById(id).orElse(null));
		modelAndView.setViewName("company/edit");
		return modelAndView;
	}

	@RequestMapping("/company/update")
	public String update(@RequestParam Long id, @RequestParam String name, @RequestParam String address) {
		Company company = companyRepository.findById(id).orElse(null);
		company.setName(name);
		company.setAddress(address);
		company.setModifiedDate(new Date());
		companyRepository.save(company);
		return "redirect:/company/show/" + company.getId();
	}
}
