package com.xws.users.controller;

import com.xws.users.dto.JwtAuthenticationRequest;
import com.xws.users.dto.RegularUserUpdateDTO;
import com.xws.users.dto.UserRegisterDTO;
import com.xws.users.dto.UserTokenState;
import com.xws.users.service.impl.RegularUserService;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.users.model.roles.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/regularuser")
public class RegularUserController {

    @Autowired
    private RegularUserService regularUserService;

    @PreAuthorize("hasRole('REGULAR')")
    @PostMapping("/getUser")
    public ResponseEntity<RegularUserUpdateDTO> addUser(@RequestBody RegularUserUpdateDTO regularUserUpdateDTO) {
        RegularUser regularUser = regularUserService.findByUsername(regularUserUpdateDTO.getUsername());

        if (regularUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new RegularUserUpdateDTO(regularUser), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('REGULAR')")
    @PutMapping(consumes = "application/json")
    public ResponseEntity<RegularUserUpdateDTO> updateUserAcc(@RequestBody RegularUser regularUser) {

        RegularUser regularUserForUpdate = regularUserService.findByUsername(regularUser.getUsername());

        if (regularUserForUpdate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        regularUserForUpdate.setName(regularUser.getName());
        regularUserForUpdate.setSurname(regularUser.getSurname());
        regularUserForUpdate.setUsername(regularUser.getUsername());
        regularUserForUpdate.setEmail(regularUser.getEmail());
        regularUserForUpdate.setPhoneNumber(regularUser.getPhoneNumber());
        regularUserForUpdate.setDateOfBirth(regularUser.getDateOfBirth());
        regularUserForUpdate.setGender(regularUser.getGender());
        regularUserForUpdate.setLinkToWebSite(regularUser.getLinkToWebSite());
        regularUserForUpdate.setBio(regularUser.getBio());

        regularUser = regularUserService.save(regularUserForUpdate);
        return new ResponseEntity<>(new RegularUserUpdateDTO(regularUser), HttpStatus.OK);
    }
}
