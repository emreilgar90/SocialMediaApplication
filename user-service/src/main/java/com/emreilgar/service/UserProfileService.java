package com.emreilgar.service;

import com.emreilgar.dto.request.NewCreateUserDto;
import com.emreilgar.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.emreilgar.dto.request.UpdateRequestDto;
import com.emreilgar.dto.response.RoleResponseDto;
import com.emreilgar.dto.response.UpdateResponseDto;
import com.emreilgar.exception.ErrorType;
import com.emreilgar.exception.UserManagerException;
import com.emreilgar.manager.IAuthManager;
import com.emreilgar.manager.IElasticManager;
import com.emreilgar.mapper.IUserMapper;
import com.emreilgar.repository.IUserProfileRepository;
import com.emreilgar.repository.entity.UserProfile;
import com.emreilgar.repository.enums.Status;
import com.emreilgar.utillity.JwtTokenManager;
import com.emreilgar.utillity.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository userProfileRepository;
    private final IAuthManager authManager;
    private final CacheManager cacheManager;
    private final IElasticManager elasticManager;
    private final JwtTokenManager jwtTokenManager;


    public UserProfileService(IUserProfileRepository userProfileRepository, IAuthManager authManager, CacheManager cacheManager, IElasticManager elasticManager, JwtTokenManager jwtTokenManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.authManager = authManager;
        this.cacheManager = cacheManager;
        this.elasticManager = elasticManager;
        this.jwtTokenManager = jwtTokenManager;
    }
    /*********************************************************************************/
    @Transactional
    public Boolean createUser(NewCreateUserDto dto) {

        try {
            UserProfile userProfile=IUserMapper.INSTANCE.toUserProfile(dto);
            userProfile.setCreateDate(System.currentTimeMillis());
            //elasticManager.create(userProfile);
            save(userProfile);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw  new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }
    /*********************************************************************************/
    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()){
            throw  new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(Status.ACTIVE);
        save(userProfile.get());
        return  true;
    }
    /*********************************************************************************************/
    public UpdateResponseDto updateProfile(UpdateRequestDto dto) {
        Optional<Long> id= verifyToken(dto.getToken());  //verifyToken metodu oluşturup dto dan id'yi döndük yukarıda

        Optional<UserProfile>userProfile= userProfileRepository.findOptionalByAuthId(id.get());

        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setName(dto.getName());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setUsername(dto.getUsername());
        userProfile.get().setEmail(dto.getEmail());
        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setUpdatedDate(System.currentTimeMillis());
        save(userProfile.get());
        return  IUserMapper.INSTANCE.toUpdateResponseDto(userProfile.get());

    }

    /******************************************** verifyToken *************************************************/
    public Optional<Long> verifyToken(String token){
        Optional<Long> id= jwtTokenManager.getUserId(token);
        if(id.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        return id;
    }
    /*********************************************************************************************/



    @Transactional
    public UpdateResponseDto updateProfile2(UpdateRequestDto dto) {
        Optional<Long> id= verifyToken(dto.getToken());  //verifyToken metodu oluşturup dto dan id'yi döndük yukarıda
        Optional<UserProfile>userProfile= userProfileRepository.findOptionalByAuthId(id.get());
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }                                            //clear deseydik evict yerine cache komple silerdi.
        cacheManager.getCache("findbyusername").evict(userProfile.get().getUsername()); //EVİCT=TAHLİYE ETME
        if(!dto.getUsername().equals(userProfile.get().getUsername()) || !dto.getEmail().equals(userProfile.get().getEmail())){
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            authManager.updateByEmailAndUsername(UpdateByEmailOrUsernameRequestDto.builder().
                    email(dto.getEmail())
                    .username(dto.getUsername())
                    .build());
            //authManager'a gönderilecek metot yazılacak
        }
        userProfile.get().setName(dto.getName());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setUsername(dto.getUsername());
        userProfile.get().setEmail(dto.getEmail());
        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setUpdatedDate(System.currentTimeMillis());
        save(userProfile.get());

        //save(IUserMapper.INSTANCE.toUserProfile(UpdateRequestDto));

        return IUserMapper.INSTANCE.toUpdateResponseDto(userProfile.get()); //dto dönüş olduğu için

    }



    public Boolean deleteByAuthId(Long Id) {
        Optional<UserProfile> userProfile= userProfileRepository.findOptionalByAuthId(Id);
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(Status.DELETED);
        save(userProfile.get());
        return true;
    }

    @Cacheable(value = "getbyrole",key="#role.toUpperCase()") //role olarak USER da user da verilse upperCASE
    public List<UserProfile> getByRole(String role) {
        List<RoleResponseDto> roleResponseDtoList= authManager.getByRole(role.toUpperCase()).getBody();
       //10.01->56:50 de anlatıyor stream kısmını!
        return roleResponseDtoList.stream()
                .map(x->userProfileRepository.findOptionalByAuthId(x.getId()).get())
                .collect(Collectors.toList());

    }
    @Cacheable(value = "findallactiveprofile")
    public List<UserProfile> getByStatusActiveProfile(){
      return userProfileRepository.findAllActiveProfile();

    }
    @Cacheable(value="findbyusername",key="#username.toLowerCase()")
    public UserProfile findByUsername(String username){
        try {
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
        Optional<UserProfile>userProfile=userProfileRepository.findOptionalByUsername(username);
        if(userProfile.isPresent()){
            return userProfile.get();
        }else {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND,"Kullanıcı Adı Bulunamadı");
        }
    }

    public Page<UserProfile> findAllPageable(int pageSize, int pageNumber, String direction, String sortpage) {
        Sort sort=Sort.by(Sort.Direction.fromString(direction),sortpage);
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        return userProfileRepository.findAll(pageable);

    }
    public Slice<UserProfile> findAllSlice(int pageSize, int pageNumber, String direction, String sortpage) {
        Sort sort=Sort.by(Sort.Direction.fromString(direction),sortpage);
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        return userProfileRepository.findAll(pageable);

    }
}
