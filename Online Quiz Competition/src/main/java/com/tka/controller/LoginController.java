package com.tka.controller;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Answer;
import com.tka.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController

public class LoginController 
{

	@Autowired
	SessionFactory factory;

	static HttpSession httpsession;
	
	@PostMapping("validate")
	public Boolean validate(@RequestBody User userfrombrowser,HttpServletRequest request)
	{
		System.out.println("user from browser " + userfrombrowser);
		
		// userfrombrowser===> [username=x  password=z  mobno=0  emailid=null]   User class object from browser
		
		Session session=factory.openSession();
		
		User userfromdatabase=session.get(User.class,userfrombrowser.getUsername());// load user object from database based on username
	
		// userfromdatabase==>[username= x  password=y  mobno=1234  emailid=sde@fxfx ]   User class object given by get method
		
		System.out.println("user from database " +userfromdatabase);
		
		
		
		// this refers to that object which is used to call method
		
		boolean answer=userfrombrowser.equals(userfromdatabase);
				
		System.out.println("answer from equals() of Object class is " + answer);
		
		if(answer) 
		{
					httpsession=request.getSession();
					
					httpsession.setAttribute("score",0);
					
					httpsession.setAttribute("questionIndex",0);
					
					
					HashMap<Integer,Answer> hashmap=new HashMap<>();
					
					httpsession.setAttribute("submittedDetails", hashmap);

					
		}
		
		return answer;
				
	}
	
	
	
	

}
