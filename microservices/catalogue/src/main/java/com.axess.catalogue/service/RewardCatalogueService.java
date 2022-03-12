package com.axess.catalogue.service;

import java.util.List;

import com.axess.catalogue.exception.RecordNotCreatedException;
import com.axess.catalogue.exception.RecordNotFoundException;
import com.axess.catalogue.model.RewardsCatalogue;

public interface RewardCatalogueService {
	
	List<RewardsCatalogue> getAll() throws RecordNotFoundException;
	RewardsCatalogue getById(long id) throws RecordNotFoundException;
	
	String saveAllItems(List<RewardsCatalogue> items) throws RecordNotCreatedException;

}
