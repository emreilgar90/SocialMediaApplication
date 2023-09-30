package com.emreilgar.controller;
import com.emreilgar.dto.request.NewCreateUserDto;
import com.emreilgar.dto.request.UpdateRequestDto;
import com.emreilgar.dto.response.UpdateResponseDto;
import com.emreilgar.repository.entity.UserProfile;
import com.emreilgar.repository.enums.Status;
import com.emreilgar.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static com.emreilgar.constants.RestApi.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));

    }
    @PutMapping(ACTIVATESTATUSBYID)
    @Transactional
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authid){
        return  ResponseEntity.ok(userProfileService.activateStatus(authid));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<UpdateResponseDto> update(@RequestBody @Valid UpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateProfile(dto));
    }
    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public ResponseEntity<UpdateResponseDto> update2(@RequestBody @Valid UpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateProfile2(dto));
    }
    @DeleteMapping(DELETEBYAUTHID+"/{authId}")
    public ResponseEntity<Boolean> deleteByAuthId(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.deleteByAuthId(authId));
    }
    @GetMapping(GETBYROLE)
    public ResponseEntity<List<UserProfile>> getByRole(@PathVariable String role){
        return ResponseEntity.ok(userProfileService.getByRole(role));

    }
    @GetMapping("/findallActiveProfile")
    public ResponseEntity<List<UserProfile>> getByStatusActiveProfile(){
        return ResponseEntity.ok(userProfileService.getByStatusActiveProfile());
    }

    @GetMapping("/findbyusername")
    public ResponseEntity<UserProfile>findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }
    @GetMapping(FINDALL)
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
}
