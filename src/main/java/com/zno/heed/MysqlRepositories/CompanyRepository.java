package com.zno.heed.MysqlRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zno.heed.MysqlEntites.Company;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	Company findByName(String name); 
	
	List<Company>findByNameLike(String name);
}