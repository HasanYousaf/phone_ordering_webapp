package com.example.phone_ordering_webapp.controller;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.phone_ordering_webapp.model.*;
import com.example.phone_ordering_webapp.service.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	
	@Autowired
	private MyUserDetailsService userService;
	
	@Autowired
	private ReceiptService receiptService;
	
	@Autowired
	private CustomerService cxService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public String loggedIn(HttpServletRequest request) {
		String email = request.getUserPrincipal().getName();
		return email;	
	}
	
	@RequestMapping("/logoutUser")
	public void Logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/register");
	}
	
	
	@GetMapping("/register")
	public String registration() {
		return "register";
	}
	
	@PostMapping("/register")
	public String createUser(@RequestParam String name, @RequestParam String email, 
			@RequestParam String password, @RequestParam String role) {
		
		Customer cx = new Customer(0, name, email);
		UserEntity user = new UserEntity(0, name, email, passwordEncoder.encode(password.strip()), role.strip());
		
		userService.save(user);
		cxService.saveCx(cx);
		
		return "register";
	}
	
	@RequestMapping("/users/home")
	public String goHome(Authentication auth, Principal p, Model model, HttpServletRequest request) {
		
		boolean isAdmin = false;
		if (auth.isAuthenticated()) {
			isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		String email = loggedIn(request);
		String username = cxService.getCustomerName(email);
		model.addAttribute("username", username);
		model.addAttribute("isAdmin", isAdmin);
	
		return "home";
	}
	
	
	@GetMapping("/admin/admin")
	public String adminPage (Authentication auth, Principal p, HttpServletRequest request, Model model) {
		
		boolean isAdmin = false;
		if (auth.isAuthenticated()) {
			isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		int total = 0;
		List<Receipt> allOrders = receiptService.getReceipts();
		for (Receipt receipt: allOrders) {
			total += receipt.getPrice();
		}
		
		String revenue = "Total Sales: $" + Integer.toString(total);
		
		String email = loggedIn(request);
		String username = cxService.getCustomerName(email);
		model.addAttribute("username", username);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("total", revenue);
		model.addAttribute("receipts", allOrders);
		
		return "admin";
		
	}
	
	@GetMapping("/users/order")
	public String createOrder (Authentication auth, Principal p, HttpServletRequest request, Model model) {
		
		boolean isAdmin = false;
		if (auth.isAuthenticated()) {
			isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		String email = loggedIn(request);
		String username = cxService.getCustomerName(email);
		model.addAttribute("username", username);
		model.addAttribute("isAdmin", isAdmin);
		return "order";
	}
	
	@RequestMapping("/users/history")
	public String orderHistory(Authentication auth, Principal p, HttpServletRequest request, Model model) {
		List<Receipt> cxOrders = receiptService.getOrdersByCx(loggedIn(request));
		
		boolean isAdmin = false;
		if (auth.isAuthenticated()) {
			isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		model.addAttribute("receipts", cxOrders);
		String email = loggedIn(request);
		String username = cxService.getCustomerName(email);
		model.addAttribute("username", username);
		model.addAttribute("isAdmin", isAdmin);
		return "history";
	}
	
	@PostMapping("/users/receipt")
	public String orderConfirmation(Authentication auth, Principal p, @RequestParam String option,
			@RequestParam String color, Model model, HttpServletRequest request) {
		
		boolean isAdmin = false;
		if (auth.isAuthenticated()) {
			isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		int price;
		switch (option) {
		case "iPhone":
			price = 800;
			break;
		default:
			price = 500;
			break;
		}	
		
		Customer cx = new Customer();
		cx = cxService.getCustomerByEmail(loggedIn(request));
		Receipt receipt = new Receipt (0, cx.getName(), cx.getEmail(), price, option, color);
		receiptService.saveReceipt(receipt);
		
		String email = loggedIn(request);
		String username = cxService.getCustomerName(email);
		model.addAttribute("username", username);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("order", receipt);
		
		return "receipt";
	}
	
	public static boolean hasRole (String roleName) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
	}
}
