package com.nagarro.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.models.Book;
import com.nagarro.services.BookApi;

@Controller
public class DeleteBookController {
	
	RestTemplate restTemplate = new RestTemplate();
	@RequestMapping("delete/{id}")
	public ModelAndView deleteBook(@PathVariable int id) {
		BookApi api = new BookApi();
		ModelAndView view = new ModelAndView();
		
		Map<String, Integer> param = new HashMap<String,Integer>(); 
		param.put("id", id);
		
		restTemplate.delete("http://localhost:8090/delete/{id}", param);

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
		view.setViewName("../home.jsp");
		return view;

	}
}
