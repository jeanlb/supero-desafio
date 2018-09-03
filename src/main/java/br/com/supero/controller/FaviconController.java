package br.com.supero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class FaviconController {
  
	/** 
	 * Retornar empty quando da requisicao ao favicon.ico.
	 * Util para evitar 404 no browser, mesmo com o favicon 
	 * desabilitado no application.properties.
	 * Web Source: https://www.baeldung.com/spring-boot-favicon
	 */
    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {}
    
}