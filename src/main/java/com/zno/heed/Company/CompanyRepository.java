package com.zno.heed.Company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public interface CompanyRepository extends JpaRepository<Company, Long> {

	Company findByName(String name); 
	
	List<Company>findByNameLike(String name);
}