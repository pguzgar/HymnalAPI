package mx.daro.playlistAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.daro.playlistAPI.entity.Playlist;

@Repository
public interface PlayListRepository extends CrudRepository<Playlist, Integer> {
 
	@Modifying (clearAutomatically = true)
	@Query(value = "UPDATE playlist SET data = ?2 WHERE playlist_id = ?1", nativeQuery=true)
	public void updateData(Integer playlistId, String data);
	
	@Query(value = "SELECT count(1) FROM playlist where user_id=?1 and user_provider = ?2", nativeQuery=true)
	public int exists(String userId, String userProvider);
	
	public Optional<Playlist> findByUserIdAndUserProvider(String userId, String userProvider);
}