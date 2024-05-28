package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.entity.Customer;
import com.example.demo.service.BaseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/customers")
public class CustomersController {
	@Autowired
	BaseService<Customer> customerService;
	
	
	@GetMapping(path = {"", "/"})
	public String list(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);
		return "admin/customers/list";
	}
	
	@GetMapping(value = "/create")
	public String form(Customer customer, Model model) {
		model.addAttribute("customer", customer);
		return "admin/customers/create";
	}
	
	@PostMapping(value = "/create")
	public String register(@Valid Customer customer, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if(result.hasErrors()) {
				return "admin/customers/create";
			}
			
			customerService.save(customer);
			flash = new FlashData().success("新規作成しました");
		}catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash",flash);
		return "redirect:/admin/customers";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			Customer customer = customerService.findById(id);
			model.addAttribute("customer", customer);
		}catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/customers";
		}
		return "admin/customers/edit";
	}
	
	@PostMapping(value = "/edit/{id}")
	public String update(@PathVariable Integer id, @Valid Customer customer, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if(result.hasErrors()) {
				return "admin/customers/edit";
			}
			customerService.findById(id);
			customerService.save(customer);
			flash = new FlashData().success("更新しました");
		}catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/customers";
	}
	

}
