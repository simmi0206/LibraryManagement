package com.nagarro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.models.Book;
import com.nagarro.models.User;
import com.nagarro.services.BookApi;
import com.nagarro.services.LoginSignupService;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginSignUpController {
	public static String user_name;
	final LoginSignupService service = new LoginSignupService();
	final BookApi api = new BookApi();

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		
		
		String username = request.getParameter("userName");
		String password = request.getParameter("password");

		if (service.isUser(username, password)) {
			System.out.println("Login Successful");
			request.getSession().setAttribute("authorized", true);
			request.getSession().setAttribute("user", service.getUser(username));
			user_name = username;
			try {
				ArrayList<Book> books=  api.getBooks();
				System.out.println(books);
				view.addObject("books", books);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			view.setViewName("home.jsp");
			view.addObject("username", username);
			
		} else {
			request.setAttribute("message", "Invalid login");
			view.setViewName("index.jsp");
		}
		return view;

	}

	@RequestMapping("/register")
	public ModelAndView signup(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView view = new ModelAndView();

		String username = request.getParameter("userName");
		String password = request.getParameter("password");

		boolean ans = service.addUser(username, password);
		if (ans) {
			System.out.println("registered successfully");
			view.setViewName("index.jsp");
		} else {
			System.out.println("not registered successfully");
			view.setViewName("registration.jsp");
		}

		return view;

	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{	
		service.logout(request, response);
	}
}
