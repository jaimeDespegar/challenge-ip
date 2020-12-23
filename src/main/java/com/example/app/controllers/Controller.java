
package com.example.app.controllers;

import com.example.app.models.CountryInformation;
import com.example.app.services.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Controller {

	@Autowired
	private IpService service;

	@RequestMapping("/")
	public String index() {
		return "Hello World!";
	}

	@RequestMapping("/{ip}")
	public CountryInformation getInformationByIp(@PathVariable String ip) {
		return this.service.getInfoUser(ip);
	}

}