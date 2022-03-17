package com.nagarro.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


import com.nagarro.models.Book;
import com.nagarro.services.BookApi;

@Controller
public class AddBookController {
	
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/addBook")
	public ModelAndView addBook() {
		ModelAndView view = new ModelAndView();
		view.addObject("username", LoginSignUpController.user_name);
		view.setViewName("add.jsp");
		return view;
	}
	
	@RequestMapping("/add")
	public ModelAndView addBookDetails(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
		BookApi api = new BookApi();
		ModelAndView view = new ModelAndView();
		
		String name = request.getParameter("bookname");
		String author = request.getParameter("author");
		
		String data = "name="+name+"&author="+author;
		
		Book book = new Book(name,author);
		ResponseEntity<Book> b = restTemplate.postForEntity("http://localhost:8090/addBook", book, Book.class);
		System.out.println(b.getBody());
		try {
			ArrayList<Book> books=  api.getBooks();
			System.out.println(books);
			view.addObject("books", books);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		view.addObject("username", LoginSignUpController.user_name);
		view.setViewName("home.jsp");
		return view;
	}
	
	@RequestMapping("/goback")
	public ModelAndView addBookDetails() throws IOException, InterruptedException {
		BookApi api = new BookApi();
		ModelAndView view = new ModelAndView();
		
		try {
			ArrayList<Book> books=  api.getBooks();
			System.out.println(books);
			view.addObject("books", books);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		view.addObject("username", LoginSignUpController.user_name);
		view.setViewName("home.jsp");
		return view;
	}
}
