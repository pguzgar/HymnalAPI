package mx.daro.himnario.properties.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.daro.himnario.properties.util.Crypto;
import mx.daro.himnario.properties.util.PropertiesUtil;

@Service
public class PropertiesService {

	Logger logger = Logger.getLogger(PropertiesService.class.getSimpleName());
	private ObjectMapper mapper = new ObjectMapper();

	@Cacheable(cacheNames = "propertiesCache", key="#key")
	public String getProperty(String key) throws Exception {
		logger.info("** data from properties file: "+key);
		Map<String, Object> map = new HashMap<>();
		map.put("value", PropertiesUtil.getProperty(key));
		return Crypto.encrypt(mapper.writeValueAsString(map));
	}
	
	@Cacheable(cacheNames = "propertiesCache", key="#key")
	public String getPropertyUnencrypted(String key) {
		logger.info("** data from properties file: "+key);
		return PropertiesUtil.getProperty(key);
	}
}
