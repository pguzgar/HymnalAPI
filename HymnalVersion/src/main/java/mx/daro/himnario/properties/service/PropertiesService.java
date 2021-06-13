package mx.daro.himnario.properties.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.daro.himnario.properties.util.Constants;
import mx.daro.himnario.properties.util.Crypto;
import mx.daro.himnario.properties.util.PropertiesUtil;

@Service
public class PropertiesService {

	Logger logger = Logger.getLogger(PropertiesService.class.getSimpleName());
	private ObjectMapper mapper = new ObjectMapper();

	@Cacheable(cacheNames = "propertiesCache")
	public String getProperty(String key) throws Exception {
		logger.info("** data from properties file: "+key);
		Map<String, Object> map = new HashMap<>();
		String value = PropertiesUtil.getProperty(key);
		if(!key.startsWith(Constants.NEWS) && !StringUtils.hasLength(value)) {
			throw new RuntimeException("property ["+key+"] not found");
		}
		map.put("value", value);
		return Crypto.encrypt(mapper.writeValueAsString(map));
	}
	
	@Cacheable(cacheNames = "propertiesCache")
	public String getProperty(String key, String version) throws Exception {
		logger.info("** data from properties file: "+key+", version: "+version);
		Map<String, Object> map = new HashMap<>();
		String value = PropertiesUtil.getProperty(key+"."+version);
		if(!StringUtils.hasLength(value)) {
			value = PropertiesUtil.getProperty(key);
		}
		if(!StringUtils.hasLength(value)) {
			throw new RuntimeException("property ["+key+"] not found");
		}
		map.put("value", value);
		return Crypto.encrypt(mapper.writeValueAsString(map));
	}
	
	@Cacheable(cacheNames = "propertiesCache")
	public String getPropertyUnencrypted(String key) {
		logger.info("** data from properties file: "+key);
		return PropertiesUtil.getProperty(key);
	}
}
