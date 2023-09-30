package com.emreilgar.manager;


import com.emreilgar.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.emreilgar.dto.response.RoleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

import static com.emreilgar.constants.RestApi.*;


@FeignClient(url = "${myapplication.feign.auth}",name = "auth-auth",decode404 = true)
public interface IAuthManager {

    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    ResponseEntity<Boolean> updateByEmailAndUsername(@RequestBody UpdateByEmailOrUsernameRequestDto dto);

    @GetMapping(GETBYROLE)
    ResponseEntity<List<RoleResponseDto>> getByRole(@PathVariable String role);



}
