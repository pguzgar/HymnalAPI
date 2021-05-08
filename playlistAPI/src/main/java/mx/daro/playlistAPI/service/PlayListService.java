package mx.daro.playlistAPI.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.daro.playlistAPI.entity.Playlist;
import mx.daro.playlistAPI.repository.PlayListRepository;

@Transactional
@Service
public class PlayListService {
	
	@Autowired
	private PlayListRepository dataDao;
		
	public Optional<Playlist> getById(Integer playlistId){
		return dataDao.findById(playlistId);
	}
	
	public Optional<Playlist> getByUserIdAndUserProvider(String userId, String userProvider){
		return dataDao.findByUserIdAndUserProvider(userId, userProvider);
	}
	
	public Playlist save(Playlist playlist) {
		return dataDao.save(playlist);
	}
	
	public void updateData(Integer playlistId,String data){
		dataDao.updateData(playlistId, data);
	}
	
	public void deleteById(Integer playlistId) {
		dataDao.deleteById(playlistId);
	}
	
	public boolean exists(String userId, String userProvider) {
		return dataDao.exists(userId, userProvider)>0;
	}
}
