package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value="/updatePassword/{username}", method = RequestMethod.POST)
	private String updateUserSubmit(@PathVariable("username") String username, String oldPassword, String newPassword, String newPasswordConf, Model model) {
		UserRoleModel user = userService.getUserByUsername(username);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (passwordEncoder.matches(oldPassword, user.getPassword())) {
				if (newPassword.equals(newPasswordConf)) {
					userService.updatePassword(newPassword, user);
					model.addAttribute("goodResponse", "Success");
				} else {
					model.addAttribute("badResponse", "Password Baru & Password Lama Berbeda");
				}
		} else {
			model.addAttribute("badResponse", "Password Lama Tidak Sesuai, Mohon Coba Kembali");
		}
		return "home";
	}
}
