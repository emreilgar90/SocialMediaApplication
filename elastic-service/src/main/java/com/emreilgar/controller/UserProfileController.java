package com.emreilgar.controller;


import com.emreilgar.repository.entity.UserProfile;
import com.emreilgar.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emreilgar.constant.RestApi.*;


@RestController
@RequestMapping(ELASTIC)
@RequiredArgsConstructor
public class UserProfileController {


    private  final UserProfileService userProfileService;


    @GetMapping(FINDALL)
    public ResponseEntity<Iterable<UserProfile>> findAll(){

        return ResponseEntity.ok(userProfileService.findAll());
    }
    @PostMapping(CREATE)
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile userProfile){
        return ResponseEntity.ok(userProfileService.save(userProfile));
    }
}
