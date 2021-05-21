package mx.daro.himnario.properties.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.daro.himnario.properties.service.PropertiesService;
import mx.daro.himnario.properties.util.Constants;

@RestController
public class ApplicationController {
	
	@Autowired private PropertiesService service;
    @Autowired private CacheManager cacheManager;
    
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
		map.put("value", service.getProperty(key));
		return map;
	}
	
	@GetMapping("/news/{version}/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getNews(@PathVariable("version") String version, @PathVariable("userId") String userId){
		Map<String, Object> map = new HashMap<>();
		String generalNews = service.getProperty(Constants.NEWS_GENERAL);
		map.put("value", generalNews);
		return map;
	}
	
	@GetMapping("/clearCache")
	@ResponseStatus(HttpStatus.OK)
	public void clearCache(){
		cacheManager.getCache("propertiesCache").clear();
	}

}
