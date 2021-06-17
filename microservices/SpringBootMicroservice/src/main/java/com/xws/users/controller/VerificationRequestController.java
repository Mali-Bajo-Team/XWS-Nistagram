package com.xws.users.controller;

import com.xws.users.dto.RegularUserImageUpdateDTO;
import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.service.IVerificationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/verification")
public class VerificationRequestController {

    @Autowired
    private IVerificationRequestService verificationRequestService;

    @PreAuthorize("hasRole('REGULAR')")
    @PostMapping
    public ResponseEntity<VerificationRequestDTO> createVerificationRequest(@RequestBody VerificationRequestDTO verificationRequestDTO) {

        System.out.println("\n\n");
        System.out.println(verificationRequestDTO.getRealName());
        System.out.println(verificationRequestDTO.getRealSurname());
        System.out.println(verificationRequestDTO.getCategory());
        System.out.println(verificationRequestDTO.getImageOfOfficialDocument());
        System.out.println("\n\n");

        VerificationRequestDTO verificationRequestDTONew = new VerificationRequestDTO();
        return new ResponseEntity<>(verificationRequestDTONew, HttpStatus.OK);
    }

}
