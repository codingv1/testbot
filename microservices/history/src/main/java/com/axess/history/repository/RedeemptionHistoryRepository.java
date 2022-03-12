package com.axess.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axess.history.model.UserRedeemptionHistory;

@Repository
public interface RedeemptionHistoryRepository extends JpaRepository<UserRedeemptionHistory, Long> {

}
