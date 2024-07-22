package com.sgsg.dra.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sgsg.dra.admin.service.OrderManageService;
import com.sgsg.dra.domain.Order;

@Controller
public class MainManageController {
	
	@Autowired
	OrderManageService orderService;
	 
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String adminMain(Model model) {
		
		try {
			List<Order> stateCountList = orderService.selectOrderStateCount();
			
			model.addAttribute("stateCountList", stateCountList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ".adminLayout";
	}
	
	
	@RequestMapping(value="/adminManagement", method=RequestMethod.GET)
	public String adminSideLayout() {
		
		return ".adminManagement";
	}
	
}