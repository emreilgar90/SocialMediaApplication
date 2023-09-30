package com.emreilgar.repository;

import com.emreilgar.dto.response.RoleResponseDto;
import com.emreilgar.repository.entity.Auth;
import com.emreilgar.repository.enums.Roles;
import com.emreilgar.repository.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {
    Optional<Auth> findOptionalByUsername(String username);

    Optional<Auth> findOptionalByUsernameAndPassword(String username,String password);


    /************************************************************************************************/
    @Query("select a from Auth as a where a.status='ACTIVE' ")
    List<Auth> getAllActivateStatus();

    List<Auth> findAllByStatus(Status status); //Status'una göre

    /************************************************************************************************/

    List<Auth> findAllOptionalByRole(Roles role);

}
