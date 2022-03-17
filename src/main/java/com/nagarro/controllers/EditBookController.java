package com.nagarro.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.models.Book;
import com.nagarro.services.BookApi;

@Controller
public class EditBookController {

	RestTemplate restTemplate = new RestTemplate();
	@RequestMapping("/update/{id}")
	public ModelAndView editBook(@PathVariable("id") int id) {
		ModelAndView view = new ModelAndView();
		
		Map<String, Integer> param = new HashMap<String,Integer>(); 
		param.put("id", id);

		Book book = restTemplate.getForObject ("http://localhost:8090/book/{id}", Book.class, param); 
		System.out.println(book.getName());
		System.out.println(book.getId());
		System.out.println(book.getAuthor());
		view.addObject("username", LoginSignUpController.user_name);
		view.addObject("book", book);
		view.setViewName("../edit.jsp");
		return view;
		
	}
	
	@RequestMapping("/updateBook")
	public ModelAndView updateBook(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("------------");
		
		BookApi api = new BookApi();
		ModelAndView view = new ModelAndView();
		
		int id = Integer.parseInt(request.getParameter("bookid"));
		String name = request.getParameter("bookname");
		String author = request.getParameter("author");
		
//		String data = "name="+name+"&author="+author;
		
		
		Book book = new Book(id,name,author);
		System.out.println(book);
//		ResponseEntity<Book> b = 
				restTemplate.put("http://localhost:8090/update", book);
//		System.out.println(b.getBody());
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
