package com.xws.users.controller;

import com.xws.users.dto.FollowerDTO;
import com.xws.users.dto.RegularUserImageUpdateDTO;
import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.service.IVerificationRequestService;
import com.xws.users.users.model.VerificationRequest;
import com.xws.users.users.model.enums.RequestStatus;
import com.xws.users.users.model.roles.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/verification")
public class VerificationController {

    @Autowired
    private IVerificationRequestService verificationRequestService;

    @PostMapping
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<VerificationRequestDTO> createVerificationRequest(@RequestBody VerificationRequestDTO verificationRequestDTO, Authentication authentication) {
        UserAccount user = (UserAccount) authentication.getPrincipal();
        verificationRequestDTO.setRequesterUsername(user.getUsername());
        VerificationRequestDTO verificationRequestDTONew = new VerificationRequestDTO(verificationRequestService.createVerificationRequest(verificationRequestDTO));
        return new ResponseEntity<>(verificationRequestDTONew, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('REGULAR')")
    // Todo: Change to administrator, this is only for test purpose
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<VerificationRequestDTO>> getAllPendingVerificationRequest() {
        List<VerificationRequestDTO> retVal = new ArrayList<VerificationRequestDTO>();
        List<VerificationRequest> verificationRequests = verificationRequestService.findAllByRequestStatus(RequestStatus.PENDING);
        for(VerificationRequest verificationRequest : verificationRequests){
            retVal.add(new VerificationRequestDTO(verificationRequest));
        }
        return ResponseEntity.ok(retVal);
    }

    @PostMapping("/accept/{id}")
    @PreAuthorize("hasRole('REGULAR')")
    // Todo: Change to administrator, this is only for test purpose
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<VerificationRequestDTO> acceptVerificationRequest(@PathVariable(required = true) Long id) {
        return ResponseEntity.ok(new VerificationRequestDTO(verificationRequestService.acceptVerificationRequest(id)));
    }

}
