
package com.example.app.controllers;

import com.example.app.models.IpInformation;
import com.example.app.services.BlackListIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

	@Autowired
	private BlackListIpService service;
	//private SimpleIpIpService service;

	@RequestMapping("/")
	public String index() {
		return "Hello World!";
	}

	@RequestMapping("/{ip}")
	public IpInformation getInformationByIp(@PathVariable String ip) {
		return this.service.getInformationUser(ip);
	}

	@PostMapping("/blackList/{ip}")
	public boolean addIpInBlackList(@PathVariable String ip) {
		return this.service.addIp(ip);
	}

	@DeleteMapping("/blackList/{ip}")
	public boolean deleteIpInBlackList(@PathVariable String ip) {
		return this.service.removeIp(ip);
	}

}