package com.xws.users.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xws.users.dto.RegularUserMiniDTO;
import com.xws.users.service.IRegularUserService;
import com.xws.users.users.model.roles.RegularUser;

@RestController
public class SearchController {

	@Autowired
	private IRegularUserService regularUserService;

	@GetMapping("search/{username}")
	@PermitAll
	public ResponseEntity<List<RegularUserMiniDTO>> search(@PathVariable(required = true) String username) {
		Collection<RegularUser> searchResult = regularUserService.searchByUsername(username);

		List<RegularUserMiniDTO> retVal = new ArrayList<RegularUserMiniDTO>();
		for (RegularUser user : searchResult) {
			retVal.add(new RegularUserMiniDTO(user));
		}

		return ResponseEntity.ok(retVal);
	}

}
