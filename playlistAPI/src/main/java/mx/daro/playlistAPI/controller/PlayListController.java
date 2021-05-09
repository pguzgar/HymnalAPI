package mx.daro.playlistAPI.controller;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.daro.playlistAPI.entity.Playlist;
import mx.daro.playlistAPI.service.PlayListService;

@RestController
@RequestMapping(path = "/playlist")
public class PlayListController {
	
	@Autowired
	public PlayListService service;
	
	private ObjectMapper mapper = new ObjectMapper();
	private Encoder encoder = Base64.getEncoder();
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getByIdPlaylist(@PathVariable("id") Integer playlistId) {
		Optional<Playlist> p = service.getById(playlistId);
		if(p!=null && p.isPresent())
			try {
				return new ResponseEntity<>(encoder.encodeToString(mapper.writeValueAsBytes(p.get().getData())), HttpStatus.OK);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{userId}/{userProvider}")
	public ResponseEntity<?> getIdByUserIdAndUserProvider(@PathVariable("userId") String userId, @PathVariable("userProvider") String userProvider) {
		Optional<Playlist> p = service.getByUserIdAndUserProvider(userId, userProvider);
		if(p!=null && p.isPresent())
			try {
				return new ResponseEntity<>(encoder.encodeToString(mapper.writeValueAsBytes(p.get().getPlaylistId())), HttpStatus.OK);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@Valid @RequestBody Playlist playlist) {
		Playlist p = null;
		if(service.exists(playlist.getUserId(), playlist.getUserProvider())) {
			Playlist current = service.getByUserIdAndUserProvider(playlist.getUserId(), playlist.getUserProvider()).get();
			current.setData(playlist.getData());
			current.setUserEmail(playlist.getUserEmail());
			current.setUserName(playlist.getUserName());
			current.setUserFirebaseId(playlist.getUserFirebaseId());
			p = service.save(current);
		}else {
			p = service.save(playlist);	
		}
		if(p!=null)
			try {
				return new ResponseEntity<>(encoder.encodeToString(mapper.writeValueAsBytes(p.getPlaylistId())), HttpStatus.OK);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void updatePlaylist(@RequestBody Playlist playlist) {
		service.updateData(playlist.getPlaylistId(), playlist.getData());
	}
	
}