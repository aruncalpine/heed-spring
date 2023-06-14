package com.zno.heed.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zno.heed.MysqlEntites.Company;
import com.zno.heed.MysqlRepositories.CompanyRepository;
import com.zno.heed.constants.CommonConstant.CompanyMessage;
import com.zno.heed.constants.CommonConstant.ResponseCode;
import com.zno.heed.utils.ZnoQuirk;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */


@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public Company getCompany(Long companyId) throws ZnoQuirk {
		Company c = companyRepository.findById(Long.valueOf(companyId)).orElse(null);
		if(c == null)
			throw new ZnoQuirk(ResponseCode.FAILED, CompanyMessage.ERROR_COMPANY_DOESNT_EXISTS);
		return c;
	}
}