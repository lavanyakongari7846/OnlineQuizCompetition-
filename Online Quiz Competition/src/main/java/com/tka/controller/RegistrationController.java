package com.tka.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.User;

@RestController

public class RegistrationController 
{
		@Autowired
		SessionFactory factory;
	
		@PostMapping("saveToDB")
		public void saveToDB(@RequestBody User user)
		{
			System.out.println(user);
			
			Session session=factory.openSession();
			
			Transaction tx=session.beginTransaction();
				
					session.persist(user);
			
			tx.commit();
			
		}

		

}






