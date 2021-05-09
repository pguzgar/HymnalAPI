package mx.daro.playlistAPI.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Playlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer playlistId;

	private String userId;
	
	private String userProvider;
	
	private String userProviderId;
	
	private String userEmail;
	
	private String userName;
	
	private String data;
	
	public Integer getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Integer playlistId) {
		this.playlistId = playlistId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserProvider() {
		return userProvider;
	}

	public void setUserProvider(String userProvider) {
		this.userProvider = userProvider;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUserProviderId() {
		return userProviderId;
	}

	public void setUserProviderId(String userProviderId) {
		this.userProviderId = userProviderId;
	}

}
