package com.after_embex.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SelfIntroController {
	// トップページを表示する
	@GetMapping("/self-intro")
	public String showTop() {
		return "index";
	}
	
	// 仁田峠の自己紹介ページを表示する
	@GetMapping("/self-intro/nitatoge")
	public String IntroNitatoge(Model model) {
		model.addAttribute("name", "仁田峠 達也");
		return "nitatoge";
	}

}
