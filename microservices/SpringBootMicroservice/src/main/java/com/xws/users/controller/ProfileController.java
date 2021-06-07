package com.xws.users.controller;

import com.xws.users.util.security.exceptions.USConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.users.dto.RegularUserUpdateDTO;
import com.xws.users.service.impl.RegularUserService;
import com.xws.users.users.model.roles.RegularUser;

@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    @Autowired
    private RegularUserService regularUserService;

    @PreAuthorize("hasRole('REGULAR')")
    @GetMapping
    public ResponseEntity<RegularUserUpdateDTO> addUser(@RequestHeader(value = "user-username") String username) {
        RegularUser regularUser = regularUserService.findByUsername(username);

        if (regularUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new RegularUserUpdateDTO(regularUser));
    }

    @PreAuthorize("hasRole('REGULAR')")
    @PutMapping(consumes = "application/json")
    public ResponseEntity<RegularUserUpdateDTO> updateUserAcc(@RequestBody RegularUserUpdateDTO regularUserUpdateDTO) {

        RegularUser regularUserForUpdate = regularUserService.findByUsername(regularUserUpdateDTO.getUsername());

        RegularUser regularUserForNewUsername = regularUserService.findByUsername(regularUserUpdateDTO.getNewusername());
        if (regularUserForUpdate == null ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (regularUserForNewUsername != null && !isUsernameChanged(regularUserUpdateDTO)) {
            throw new USConflictException("The username is already taken.");
        }

        regularUserForUpdate.setName(regularUserUpdateDTO.getName());
        regularUserForUpdate.setSurname(regularUserUpdateDTO.getSurname());
        regularUserForUpdate.setUsername(regularUserUpdateDTO.getNewusername());
        regularUserForUpdate.setEmail(regularUserUpdateDTO.getEmail());
        regularUserForUpdate.setPhoneNumber(regularUserUpdateDTO.getPhonenumber());
        regularUserForUpdate.setDateOfBirth(regularUserUpdateDTO.getBirthdaydate());
        regularUserForUpdate.setGender(regularUserUpdateDTO.getGender());
        regularUserForUpdate.setLinkToWebSite(regularUserUpdateDTO.getWebsite());
        regularUserForUpdate.setBio(regularUserUpdateDTO.getBio());

        RegularUser regularUserForUpdated = regularUserService.save(regularUserForUpdate);
        return new ResponseEntity<>(new RegularUserUpdateDTO(regularUserForUpdated), HttpStatus.OK);
    }

    private boolean isUsernameChanged(RegularUserUpdateDTO regularUserUpdateDTO) {
        return regularUserUpdateDTO.getUsername().equals(regularUserUpdateDTO.getNewusername());
    }
}
