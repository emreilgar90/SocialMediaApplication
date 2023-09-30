package com.emreilgar.service;

import com.emreilgar.dto.request.ActivateRequestDto;
import com.emreilgar.dto.request.LoginRequestDto;
import com.emreilgar.dto.request.RegisterRequestDto;
import com.emreilgar.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.emreilgar.dto.response.ActivateResponseDto;
import com.emreilgar.dto.response.LoginResponseDto;
import com.emreilgar.dto.response.RegisterResponseDto;
import com.emreilgar.dto.response.RoleResponseDto;
import com.emreilgar.exception.AuthManagerException;
import com.emreilgar.exception.ErrorType;
import com.emreilgar.manager.IUserManager;
import com.emreilgar.mapper.IAuthMapper;
import com.emreilgar.rabbitmq.producer.RegisterUserProducer;
import com.emreilgar.repository.IAuthRepository;
import com.emreilgar.repository.entity.Auth;
import com.emreilgar.repository.enums.Roles;
import com.emreilgar.repository.enums.Status;
import com.emreilgar.utillity.CodeGenerator;
import com.emreilgar.utillity.JwtTokenManager;
import com.emreilgar.utillity.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final CacheManager cacheManager;
    private final JwtTokenManager jwtTokenManager;
    private final RegisterUserProducer registerUserProducer;

    public AuthService(JpaRepository<Auth, Long> repository, IAuthRepository authRepository, IUserManager userManager, CacheManager cacheManager, JwtTokenManager jwtTokenManager, RegisterUserProducer registerUserProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;//@EnableFeignClient anatasyonunu yazmazsan hata alınır.
        this.cacheManager = cacheManager;
        this.jwtTokenManager = jwtTokenManager;
        this.registerUserProducer = registerUserProducer;
    }
    /**********************************************************************************************/
    //@Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto); //dto dan bir Auth yarat

        if(authRepository.findOptionalByUsername(auth.getUsername()).isPresent()){
            throw new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
        }
        try {
            auth.setActivationCode(CodeGenerator.generateCode());//code üretip setledik.
            userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUserDto(auth)); //UserManager feigCleint
            save(auth);
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
        }
    }
    @Transactional
    public RegisterResponseDto registerWithRabbitMQ(RegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto); //dto dan bir Auth yarat

        if(authRepository.findOptionalByUsername(auth.getUsername()).isPresent()){
            throw new AuthManagerException(ErrorType.USERNAME_DUPLICATE);
        }
        try {
            auth.setActivationCode(CodeGenerator.generateCode());//code üretip setledik.
            save(auth);
            registerUserProducer.sendNewUser(IAuthMapper.INSTANCE.toNewCreateUserModel(auth));
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    /**********************************************************************************************/
    public ActivateResponseDto activateStatus(ActivateRequestDto dto) {
        Optional<Auth> auth=findById(dto.getId()) ;
        ActivateResponseDto responseDto=null;
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (auth.get().getActivationCode().equals(dto.getActivationCode())){
            auth.get().setStatus(Status.ACTIVE);
            save(auth.get());
            cacheManager.getCache("findallactiveprofile").clear();  //yeni kullanıcı geldiğinde cache temizlenecek 13.01->2:55
            // userprofile controller a bir id gonderebilirim
            userManager.activateStatus(dto.getId());
            //cacheManager.getCache("findallactiveprofile").clear();
            responseDto=IAuthMapper.INSTANCE.toActivateResponseDto(auth.get());
        }
        return responseDto;
    }


    /**********************************************************************************************/

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR_WRONG);
        }if(!auth.get().getStatus().equals(Status.ACTIVE)){
            throw new AuthManagerException(ErrorType.LOGIN_STATUS_ERROR);
        }
        LoginResponseDto loginResponseDto= IAuthMapper.INSTANCE.toLoginResponseDto(auth.get());
        loginResponseDto.setToken(jwtTokenManager.createToken(auth.get().getId()));
        return loginResponseDto;
    }

    public List<ActivateResponseDto> getAllActiveStatus() {
        List<Auth> authList = authRepository.getAllActivateStatus();
        return IAuthMapper.INSTANCE.toActivateResponseDto(authList);
    }


    public List<ActivateResponseDto> findAllByStatus(Status status) {
        List<Auth> authList = authRepository.findAllByStatus(status);
        return IAuthMapper.INSTANCE.toActivateResponseDto(authList);
    }

    public List<ActivateResponseDto> findAllByStatusString(String status) {
        Status status1= null;
        try {
            status1= Status.valueOf(status.toUpperCase());
            List<Auth> authList= authRepository.findAllByStatus(status1);
            return IAuthMapper.INSTANCE.toActivateResponseDto(authList);
        }catch (Exception e){
            throw new AuthManagerException(ErrorType.STATUS_NOT_FOUND);

        }
    }
    public Boolean deleteAuthById(Long authId) {
        Optional<Auth> auth= findById(authId);
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(Status.DELETED);
        save(auth.get());
        userManager.deleteByAuthId(authId);
        return true;
    }

    public Boolean activateStatus2(Long authId) {
        Optional<Auth> auth= authRepository.findById(authId);
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(Status.ACTIVE);
        save(auth.get());
        userManager.activateStatus(authId);

        return true;
    }

    public Boolean updateByEmailAndUsername(UpdateByEmailOrUsernameRequestDto dto) {
        Optional<Auth> auth= authRepository.findById(dto.getId());
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        save(auth.get());
        return true;
    }

    public List<RoleResponseDto> getByRole(String role) {
      Roles roles1= null;
      try {
          roles1= Roles.valueOf(role.toUpperCase());
          return IAuthMapper.INSTANCE.toRoleResponseDtos(authRepository.findAllOptionalByRole(roles1));
      }catch (Exception e){
          throw new AuthManagerException(ErrorType.ROLE_NOT_FOUND);

      }


    }
}












