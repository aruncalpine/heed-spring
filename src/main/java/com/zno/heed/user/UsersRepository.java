package com.zno.heed.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public interface UsersRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	User findByEmailAndPassword(String email, String password);
	
	User findByUserToken(String token);

	User findByUserTokenAndAccountLockedAndEnabledAndPasswordExpired(String token, boolean b, boolean c, boolean d);
	
	@Transactional
	@Modifying
	@Query("update User u set u.userToken = null where u.email = :email")
	void setValueForUserToken( @Param("email") String email);
}