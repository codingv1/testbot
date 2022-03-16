package com.axess.ccuser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axess.ccuser.model.CCUser;

@Repository
public interface CCUserRepository extends JpaRepository<CCUser, Long> {

    @Query(value = "select cc_number, available_redeem_points, cc_name, password, " +
            "total_rewards_gained, user_id, user_name,token,mail_adress " +
            "from ccuser where user_id = ?1 and password = ?2", nativeQuery=true)
    Optional<CCUser> findByUserIdAndPassword(String userId, String password);

    @Query(value = "select cc_number, available_redeem_points, cc_name, password, " +
            "total_rewards_gained, user_id, user_name,token ,mail_adress " +
            "from ccuser where user_id = ?1 and token = ?2", nativeQuery=true)
    Optional<CCUser> getUserbyIdandToken(String userId, String token);

    @Modifying
    @Query(value = "update ccuser set token =?1 where user_id =?2", nativeQuery=true)
    void updateUser(String token, String userId);

}
