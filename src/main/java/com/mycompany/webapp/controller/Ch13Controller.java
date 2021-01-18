package com.mycompany.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ch13Controller")
@RequestMapping("/ch13")
public class Ch13Controller {
	private static final Logger logger=
			LoggerFactory.getLogger(Ch13Controller.class);
	
	@GetMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch13/content";
	}
}
