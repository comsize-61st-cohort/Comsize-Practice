package com.after_embex.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SelfIntroController {
	@GetMapping("/nitatoge")
	public String IntroNitatoge() {
		return "nitatoge";
	}
	
	@GetMapping("/sasaki")
	public String IntroSasaki() {
		return "sasaki";
	}

}
