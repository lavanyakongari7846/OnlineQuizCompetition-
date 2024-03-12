package com.tka.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Answer;
import com.tka.entity.Question;

import jakarta.servlet.http.HttpSession;

@RestController

public class QuestionController 
{
	@Autowired
	SessionFactory factory;


	
	@GetMapping("getFirstQuestion/{subjectFromAngular}")
	public Question getFirstQuestion(@PathVariable String subjectFromAngular)
	{
		HttpSession httpsession=LoginController.httpsession;
		
		Session session=factory.openSession();
				
		Query query=session.createQuery("from Question where subject=:subjectName");
		
		query.setParameter("subjectName",subjectFromAngular);
		
		List<Question> list=query.list();
		
		httpsession.setAttribute("allquestions",list);
				
		Question question=list.get(0);
				
		return question;
			
	}
	@GetMapping("nextQuestion")
	public Question nextQuestion()
	{
		HttpSession httpsession=LoginController.httpsession;
		List<Question> list=(List<Question>) httpsession.getAttribute("allquestions");
		if((int)httpsession.getAttribute("questionIndex")<list.size()-1) {
			httpsession.setAttribute("questionIndex", (int)httpsession.getAttribute("questionIndex")+1);
			Question question=list.get((int)httpsession.getAttribute("questionIndex"));
			return question;
		}
		else
		{
			httpsession.setAttribute("questionIndex",0);
					
			Question question=list.get(0);
			
			return question;
			
		}
			
	}
	@GetMapping("previousQuestion")
	public Question previousQuestion() {
	    HttpSession httpsession = LoginController.httpsession;
	    List<Question> list = (List<Question>) httpsession.getAttribute("allquestions");
	    int currentIndex = (int) httpsession.getAttribute("questionIndex");

	    // Calculate the index of the previous question
	    int previousIndex = currentIndex - 1;
	    
	    // Wrap around to the last question if currentIndex is at the beginning
	    if (previousIndex < 0) {
	        previousIndex = list.size() - 1;
	    }

	    // Update session attribute with the new index
	    httpsession.setAttribute("questionIndex", previousIndex);

	    // Retrieve the previous question
	    Question question = list.get(previousIndex);
	    
	    return question;
	} 

		@PostMapping("saveAnswer")
	public void saveAnswer(@RequestBody Answer answer) {
		HttpSession httpsession = LoginController.httpsession;
		HashMap<Integer,Answer> hashmap=(HashMap) httpsession.getAttribute("submittedDetails");
		hashmap.put(answer.qno, answer);
		System.out.println(hashmap);
	}
		@GetMapping("calculateScore")
		public Integer calculateScore(){
			HttpSession httpsession = LoginController.httpsession;
			HashMap<Integer,Answer> hashmap=(HashMap) httpsession.getAttribute("submittedDetails");
				Collection<Answer> collection =hashmap.values();
				for (Answer answer : collection) {
					if (answer.submittedAnswer.equals(answer.originalAnswer)) {
						httpsession.setAttribute("score", (int)httpsession.getAttribute("score")+1);
					}
				}
				return (int)httpsession.getAttribute("score");
		}
	
	}
	
	

