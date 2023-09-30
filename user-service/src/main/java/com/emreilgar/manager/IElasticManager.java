package com.emreilgar.manager;

import com.emreilgar.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.emreilgar.constants.RestApi.CREATE;

@FeignClient(url = "${myapplication.feign.elastic}",name = "user-elastic",decode404 = true)
public interface IElasticManager {

    @PostMapping(CREATE)
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile userProfile);
}
