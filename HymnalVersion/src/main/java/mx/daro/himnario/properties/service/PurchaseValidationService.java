package mx.daro.himnario.properties.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import mx.daro.himnario.properties.vo.Purchase;

@Service
public class PurchaseValidationService {

	Logger logger = Logger.getLogger(PurchaseValidationService.class.getSimpleName());
	private GoogleCredentials credential;
	
	public boolean validate(Purchase purchase) {
		boolean valid;
		if(purchase.getSku().contains(".pro")) {
			valid = validatePurchase(purchase);
		}else {
			valid = validateSubscription(purchase);
		}
		logger.info("Verifying product ["+purchase.getSku()+"] ["+purchase.getToken()+"] = "+valid);
		return valid;
	}
	
	public boolean validateSubscription(Purchase purchase) {
		try {
			AndroidPublisher pub = new AndroidPublisher.Builder(GoogleNetHttpTransport.newTrustedTransport(),JacksonFactory.getDefaultInstance(),
			        new HttpCredentialsAdapter(getCredential())
			).build();
			SubscriptionPurchase p = pub.purchases().subscriptions().get("mx.daro.himnario",purchase.getSku(),purchase.getToken()).execute();
			Date expiration = new Date(p.getExpiryTimeMillis());
			logger.info("Product ["+purchase.getSku()+"] ["+purchase.getToken()+"] expiration = "+expiration);
			return new Date().compareTo(expiration)<=0;
		}catch(Exception e) {
			logger.info("Error while verifying subscription ["+purchase.getSku()+"] = "+purchase.getToken());
			e.printStackTrace();
			if(StringUtils.hasLength(e.getMessage())) {
				if(e.getMessage().contains("nvalid")) return false;
				if(e.getMessage().contains("token does not match")) return false;
			}
		}
		return true;
	}
	
	public boolean validatePurchase(Purchase purchase) {
		try {
			AndroidPublisher pub = new AndroidPublisher.Builder(GoogleNetHttpTransport.newTrustedTransport(),JacksonFactory.getDefaultInstance(),
			        new HttpCredentialsAdapter(getCredential())
			).build();
			ProductPurchase p  = pub.purchases().products().get(System.getProperty("packageName"),purchase.getSku(),purchase.getToken()).execute();
			return p.getPurchaseState()==0;
		}catch(Exception e) {
			logger.info("Error while verifying inapp product ["+purchase.getSku()+"] = "+purchase.getToken());
			e.printStackTrace();
			if(StringUtils.hasLength(e.getMessage())) {
				if(e.getMessage().contains("nvalid")) return false;
				if(e.getMessage().contains("token does not match")) return false;
			}
		}
		return true;
	}
	
	public GoogleCredentials getCredential() {
		if(credential==null) {
			try {
				credential = ServiceAccountCredentials.fromStream(new FileInputStream(System.getProperty("serviceFile")))
						.createScoped("https://www.googleapis.com/auth/androidpublisher");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return credential;
	}
}
