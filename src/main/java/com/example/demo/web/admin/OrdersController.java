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

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Order;
import com.example.demo.service.BaseService;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/admin/orders")
public class OrdersController {
	@Autowired
	BaseService<Order> orderService;
	
	
	@GetMapping(path = {"","/"})
	public String list(Model model) {
		
		List<Order> orders = orderService.findAll();
		model.addAttribute("orders", orders);
		return "admin/orders/list";
	}
	
	@GetMapping(value = "/create")
	public String form(Order order, Model model) {
		model.addAttribute("order", order);
		return "admin/orders/create";
	}
	
	@PostMapping(value = "/create")
	public String register(@Valid Order order, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/orders/create";
			}
			
			orderService.save(order);
			flash = new FlashData().success("新規作成しました");
		} catch(Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/orders";
	}
	//ワーク25
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable Integer id, Model model, RedirectAttributes ra) throws DataNotFoundException{
			Order order = orderService.findById(id);
			model.addAttribute("order", order);
		return "admin/orders/view";
	}
	
	//ワーク26-1
	@GetMapping(value = "/edit/{id}")
	public String register(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			Order order = orderService.findById(id);
			model.addAttribute("order", order);
		}catch (Exception e) {
			FlashData flash = new FlashData().danger("");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/orders";
	    }
		return "admin/orders/edit";
	}	
	
	//ワーク26-2
	@PostMapping(value = "/edit/{id}")
	public String update(@PathVariable Integer id, @Valid Order order, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if(result.hasErrors()) {
				return "admin/orders/edit";
			}
			orderService.findById(id);
			orderService.save(order);
			flash = new FlashData().success("更新しました");
		}catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/orders";
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/orders/view/"+id;
	}
	

}
