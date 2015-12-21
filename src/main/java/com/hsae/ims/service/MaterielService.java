package com.hsae.ims.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Materiel;
import com.hsae.ims.repository.MaterielRepository;


@Service
public class MaterielService {
	
	@Autowired
	private MaterielRepository materielRepository;

	public List<Materiel> getAllMateriel(){
		return (List<Materiel>) materielRepository.findAll();
	}

}
