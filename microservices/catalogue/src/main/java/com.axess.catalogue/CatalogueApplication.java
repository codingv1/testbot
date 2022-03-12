package com.axess.catalogue;

import com.axess.catalogue.model.RewardsCatalogue;
import com.axess.catalogue.service.RewardCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CatalogueApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogueApplication.class);
	@Autowired
	private RewardCatalogueService rcService;

	public static void main(String[] args) {
		SpringApplication.run(CatalogueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		LOGGER.info("Smart Bank for catalogue service started");
		LOGGER.info(this.rcService.saveAllItems(this.loadCatalogueData()));

	}
	
	private List<RewardsCatalogue> loadCatalogueData() {
		List<RewardsCatalogue> catalogueItems = new ArrayList<>();
		
		RewardsCatalogue item = new RewardsCatalogue();
		item.setItem("Amazon Voucher");
		item.setRedeemptionAmount(500);
		item.setRedeemptionPoint(1500);
		catalogueItems.add(item);
		
		item = new RewardsCatalogue();
		item.setItem("Amazon Voucher");
		item.setRedeemptionAmount(750);
		item.setRedeemptionPoint(2500);
		catalogueItems.add(item);
		
		item = new RewardsCatalogue();
		item.setItem("Starbucks Voucher");
		item.setRedeemptionAmount(350);
		item.setRedeemptionPoint(1000);
		catalogueItems.add(item);
		
		item = new RewardsCatalogue();
		item.setItem("Walmart Voucher");
		item.setRedeemptionAmount(500);
		item.setRedeemptionPoint(1500);
		catalogueItems.add(item);
		
		item = new RewardsCatalogue();
		item.setItem("Uber Voucher");
		item.setRedeemptionAmount(500);
		item.setRedeemptionPoint(1500);
		catalogueItems.add(item);
		
		
		return catalogueItems;
	}


}
