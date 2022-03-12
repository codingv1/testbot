package com.axess.catalogue.repository;

import com.axess.catalogue.model.RewardsCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardsCatalogueRepository extends JpaRepository<RewardsCatalogue, Long>{


    @Query(value = "select * from rewards_catalogue where id in (select  min(id) from rewards_catalogue\n" +
            "group by item, redeemption_point, redeemption_amount)", nativeQuery=true)
    List<RewardsCatalogue> GetDeduplicaeData();
}
