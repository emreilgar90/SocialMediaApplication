package com.emreilgar.manager;
import com.emreilgar.dto.request.NewCreateUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emreilgar.constants.RestApi.*;

/*05.01->3:02*/
@FeignClient(url = "${myapplication.feign.user.profile}",name = "user-userprofile",decode404 = true)
public interface IUserManager {
    @PostMapping(CREATE)
    ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserDto dto);

    @PutMapping(ACTIVATESTATUSBYID)

    ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);

    @DeleteMapping(DELETEBYAUTHID+"/{authId}")
    ResponseEntity<Boolean> deleteByAuthId(@PathVariable Long authId);


}
