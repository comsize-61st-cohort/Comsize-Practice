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
	
	// 中出の自己紹介ページを表示する
	@GetMapping("/self-intro/nakaide")
	public String IntroNakaide(Model model) {
		return "nakaide";
	}
	
	// 佐々木の自己紹介ページを表示する
	@GetMapping("/self-intro/sasaki")
	public String IntroSasaki() {
		return "sasaki";
	}
	
	// 佐藤の自己紹介ページを表示する
    @GetMapping("/self-intro/sato")
    public String IntroSato() {
      return "sato";
    }
    
    // 仁田峠の自己紹介ページを表示する
 	@GetMapping("/self-intro/nitatoge")
 	public String IntroNitatoge(Model model) {
 		model.addAttribute("name", "仁田峠 達也");
 		return "nitatoge";
 	}
}
