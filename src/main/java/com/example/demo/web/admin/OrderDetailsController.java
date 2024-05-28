package com.example.demo.web.admin;

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
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.service.BaseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/orderdetails")
public class OrderDetailsController {
	@Autowired
	BaseService<OrderDetail> orderdetailService;
	@Autowired
	BaseService<Order> orderService;
	
	//ワーク27-1 受注詳細を新規登録する画面表示
	@GetMapping(value = "/create/{orderId}")
	public String form(@PathVariable Integer orderId, OrderDetail orderdetail, Model model) {
		model.addAttribute("orderdetail", orderdetail);
		model.addAttribute("id", orderId);
	
		try {
			orderService.findById(orderId);
		}catch (Exception e) {
			return "redirect:/admin/orders";
		}
		return "admin/orderdetails/create";
		
		
	}
	
	//ワーク27-2 受注詳細を新規登録する
	@PostMapping(value = "/create/{orderId}")
	public String register(@PathVariable Integer orderId,@Valid OrderDetail orderdetail, BindingResult result, Model model, RedirectAttributes ra) {
		
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/orderdetails/create";
			}

			Order order = orderService.findById(orderId);
			orderdetail.setOrder(order);
			orderdetailService.save(orderdetail);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
			return "redirect:/admin/orders";
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/orders/view/"+orderId;
	} 
	

}
