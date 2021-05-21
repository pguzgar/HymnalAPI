package mx.daro.himnario.properties.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.daro.himnario.properties.service.PropertiesService;

@RestController
public class ApplicationController {
	
	@Autowired private PropertiesService service;
	
	@GetMapping("/version")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getByVersionId(){
		Map<String, Object> map = new HashMap<>();
		map.put("versionId", 1L);
		map.put("number", Long.valueOf(service.getProperty("version")));
		return map;
	}

	@GetMapping("/prop/{key}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getProperty(@PathVariable("key") String key){
		Map<String, Object> map = new HashMap<>();
		map.put("key", key);
		map.put("value", service.getProperty(key));
		return map;
	}

}
