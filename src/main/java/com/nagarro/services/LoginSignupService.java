package com.nagarro.services;

import java.io.IOException;
import java.net.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;


import com.nagarro.hibernate.Hibernate;
import com.nagarro.models.User;

public class LoginSignupService {
	
	public static Session session = Hibernate.getSession();
	public boolean isUser(String userName, String password) {
		try {
			
			Transaction transaction = session.beginTransaction();
			User user = session.get(User.class, userName);
			transaction.commit();
//			System.out.println(user.getUsername());
			if(user.getUsername() != null && user.getPassword().equals(password)) {
				return true;
			}else {
				return false;
			}	

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}

	public User getUser(String username) {
		User user = null;
		try {
			user = session.get(User.class, username);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return user;
	}

	public boolean addUser(String username, String password) {
		User user = null;
		try {
			Transaction transaction = session.beginTransaction();
			user=new User(username, password);
			
			session.save(user);
			transaction.commit();
		}catch(Exception e) {
			System.out.println(e);
		}
		if(user==null)
			return false;
		else
			return true;
	}
	
	public void logout(HttpServletRequest request , HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		session.removeAttribute("username");
		session.invalidate();
		response.sendRedirect("index.jsp");
	}
}
