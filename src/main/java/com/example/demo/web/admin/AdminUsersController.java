package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Update;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
	@Autowired
	UserService userService;
	
	
	@GetMapping(path = {"","/"})
	public String list(Model model) {
		
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "admin/users/list";
	}
	
	@GetMapping(value = "/edit")
	public String edit(Model model, RedirectAttributes ra) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User editUser;
		try {
			editUser = userService.findByEmail(email);
			
			editUser.setPassword("");
		}catch(DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin";
		}
		
		model.addAttribute(editUser);
		return "admin/users/edit";
	}
	
	
	@PostMapping(value = "/edit")
	public String update(@Validated(Update.class) User editUser, BindingResult result, Model model, RedirectAttributes ra) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		FlashData flash;
		try {
			User authUser = userService.findByEmail(email);
			if (result.hasErrors()) {
				model.addAttribute(editUser);
				return "admin/users/edit";
			}
			if (!authUser.getEmail().equals(editUser.getEmail()) && 
					!userService.isUnique(editUser.getEmail())) {
				flash = new FlashData().danger("Emailが重複しています");
				model.addAttribute("flash", flash);
				return "admin/users/edit";
				}
			
			authUser.setEmail(editUser.getEmail());
			authUser.encodePassword(editUser.getPassword());
			userService.save(authUser);
			flash = new FlashData().success("更新しました");
		} catch (DataNotFoundException e) {
			flash =new FlashData().danger("該当データがありません!");
		} catch (Exception e) {
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}

}
