package mx.daro.himnario.properties.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.daro.himnario.properties.service.PropertiesService;
import mx.daro.himnario.properties.service.PurchaseValidationService;
import mx.daro.himnario.properties.util.Constants;
import mx.daro.himnario.properties.vo.Purchase;

@RestController
public class ApplicationController {
		
	@Autowired private PropertiesService service;
    @Autowired private CacheManager cacheManager;
    @Autowired private PurchaseValidationService validationService;
    
	@GetMapping("/version")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> getByVersionId(){
		Map<String, Object> map = new HashMap<>();
		map.put("versionId", 1L);
		map.put("number", Long.valueOf(service.getPropertyUnencrypted("version")));
		return map;
	}

	@GetMapping("/prop/{key}")
	public ResponseEntity<?> getProperty(@PathVariable("key") String key){
		try {
			return new ResponseEntity<>(service.getProperty(key), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/news/{version}/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getNews(@PathVariable("version") String version, @PathVariable("userId") String userId){
		try {
			return new ResponseEntity<>(service.getProperty(Constants.NEWS_GENERAL), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/clearCache")
	@ResponseStatus(HttpStatus.OK)
	public void clearCache(){
		cacheManager.getCache("propertiesCache").clear();
	}
	
	@PostMapping("/validate")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> validate(@RequestBody Purchase purchase){
		Map<String, Object> response = new HashMap<>();
		response.put("valid", validationService.validate(purchase));
		return response;
	}

}
