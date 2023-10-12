package com.emreilgar.controller;

import com.emreilgar.dto.request.ActivateRequestDto;
import com.emreilgar.dto.request.LoginRequestDto;
import com.emreilgar.dto.request.RegisterRequestDto;
import com.emreilgar.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.emreilgar.dto.response.ActivateResponseDto;
import com.emreilgar.dto.response.LoginResponseDto;
import com.emreilgar.dto.response.RegisterResponseDto;
import com.emreilgar.dto.response.RoleResponseDto;
import com.emreilgar.repository.enums.Status;
import com.emreilgar.service.AuthService;
import com.emreilgar.utillity.JwtTokenManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.emreilgar.constants.RestApi.*;
import static com.emreilgar.constants.RestApi.GETBYROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;
    private final CacheManager cacheManager; //cache metodunu buradan çalıştıracak şekilde yazdık.
    //normalde service den çalışması gerekir.
    private final JwtTokenManager jwtTokenManager;
    private static int counter;

    @GetMapping("/getToken")
    public ResponseEntity<String> createToken(long id){
        return ResponseEntity.ok(jwtTokenManager.createToken(id));
    }
    @GetMapping("/getid")
    public ResponseEntity<Long> getId(String token){
        return ResponseEntity.ok(jwtTokenManager.getUserId(token).get());
    }


    @PostMapping(REGISTER)
    @Operation(summary = "Kullanici kayit metodu")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(REGISTER+"withrabbitmq")
    @Operation(summary = "Kullanici kayit metodu")
    public ResponseEntity<RegisterResponseDto> registerWithRabbitMq(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.registerWithRabbitMQ(dto));
    }

    @PostMapping(ACTIVATESTATUS)
    @Operation(summary = "Kullanıcıyı Aktif Etme")
    public ResponseEntity<ActivateResponseDto> activateStatus(@RequestBody ActivateRequestDto dto) {
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    /***********************************************************************************/
    @PostMapping(ACTIVATESTATUS2)
    @Operation(summary = "Kullanıcı aktif etme2")
    public ResponseEntity<Boolean> activateStatus2(@PathVariable Long authId) {
        return ResponseEntity.ok(authService.activateStatus2(authId));
    }

    /***********************************************************************************/


    @PostMapping(LOGIN)
    @Operation(summary = "Giriş")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @GetMapping(GETALLACTIVATESTATUS)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Aktif olan kişileri dönen metot")
    public ResponseEntity<List<ActivateResponseDto>> getAllActiveStatus() {
        return ResponseEntity.ok(authService.getAllActiveStatus());
    }

    @GetMapping(GETALLBYSTATUS)
    public ResponseEntity<List<ActivateResponseDto>> findAllByStatus(Status status) {
        return ResponseEntity.ok(authService.findAllByStatus(status));
    }

    @GetMapping(GETALLBYSTATUS + "/{status}")
    public ResponseEntity<List<ActivateResponseDto>> findAllByStatus2(@PathVariable Status status) {
        return ResponseEntity.ok(authService.findAllByStatus(status));
    }

    @GetMapping(GETALLBYSTRINGSTATUS + "/{stringStatus}")
    public ResponseEntity<List<ActivateResponseDto>> findAllByStatus3(@PathVariable String status) {
        return ResponseEntity.ok(authService.findAllByStatusString(status));
    }

    @DeleteMapping(DELETEBYAUTHID + "/{authId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(authService.deleteAuthById(id));
    }

    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public ResponseEntity<Boolean> updateByEmailAndUsername(@RequestBody UpdateByEmailOrUsernameRequestDto dto) {
        return ResponseEntity.ok(authService.updateByEmailAndUsername(dto));
    }

    @GetMapping(GETBYROLE)
    public ResponseEntity<List<RoleResponseDto>> getByRole(@PathVariable String role){
        return ResponseEntity.ok(authService.getByRole(role));

    }
    /***********************************    REDIS   ************************************************/
    @GetMapping("/redis")
    @Cacheable(value = "redisexample")
    public String redisExample(String value){
        try {
            Thread.sleep(2000);
            counter++;
        }catch (Exception e){
            throw new RuntimeException(e);
        }if(counter==3){  //amaç belirli bir önbellekleme olduğunda belleği temizleme !
            cacheManager.getCache("redisexample").clear();
            counter=1; //cache sıfırladık tekrar başa döndü.son veri kaldı.
            return "Tüm veriler silindi";
        }
        return value;
    }
    @GetMapping("/redisdelete")
    //@CacheEvict(cacheNames = "redisexample",allEntries = true) //cacheEvict = ön belleği boşalt
    public Boolean  redisDeleteExample(){        /*String value->spesifik değer silmek için bunu ver içine*/
        try {
            //cacheManager.getCache("redisexample").evict(value); ->spesfik bir değer silmek için
            cacheManager.getCache("redisexample").clear();//->aynı isimli cache de ki tüm değerleri silmek için
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/redisdelete2")
    @CacheEvict(cacheNames = "redisexample",allEntries = true) //cacheEvict = ön belleği boşalt
    public void  redisDeleteExample2(){
    }
    /*******************************************************************************/

}
