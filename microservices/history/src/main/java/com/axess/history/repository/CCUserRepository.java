package com.axess.history.repository;

import com.axess.history.model.CCUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CCUserRepository extends JpaRepository<CCUser, Long> {

    @Query(value = "select cc_number, available_redeem_points, cc_name, password, " +
            "total_rewards_gained, user_id, user_name " +
            "from ccuser where user_id = ?1 and password = ?2", nativeQuery=true)
    Optional<CCUser> findByUserIdAndPassword(String userId, String password);

}
