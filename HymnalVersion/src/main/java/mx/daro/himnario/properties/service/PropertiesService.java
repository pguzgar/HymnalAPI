package mx.daro.himnario.properties.service;

import java.util.logging.Logger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import mx.daro.himnario.properties.util.PropertiesUtil;

@Service
public class PropertiesService {

	Logger logger = Logger.getLogger(PropertiesService.class.getSimpleName());


	@Cacheable(cacheNames = "propertiesCache", key="#key")
	public String getProperty(String key) {
		logger.info("** data from properties file: "+key);
		return PropertiesUtil.getProperty(key);
	}
}
