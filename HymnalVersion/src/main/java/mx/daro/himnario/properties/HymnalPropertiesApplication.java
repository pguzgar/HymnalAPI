package mx.daro.himnario.properties;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HymnalPropertiesApplication {

	@Value("${ajp.port}")
	int ajpPort;
	
	public static void main(String[] args) {
		SpringApplication.run(HymnalPropertiesApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
		return server -> {
			if (server instanceof TomcatServletWebServerFactory) {
				((TomcatServletWebServerFactory) server).addAdditionalTomcatConnectors(redirectConnector());
			}
		};
	}

	private Connector redirectConnector() {
		Connector connector = new Connector("AJP/1.3");
		connector.setScheme("http");
		connector.setPort(ajpPort);
		connector.setSecure(false);
		connector.setAllowTrace(true);
	    ((AbstractAjpProtocol<?>) connector.getProtocolHandler()).setSecretRequired(false);
		return connector;
	}
}
