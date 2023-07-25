package com.zno.heed.MysqlRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zno.heed.MysqlEntites.User;

import jakarta.transaction.Transactional;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged
 * information. It is for the use of the ZNO only, and access by anyone else is
 * unauthorized.
 * 
 * Module : zno_online_class Created Date : 28/07/2020 Created By : TITTU
 * VARGHESE
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

	User findByUserToken(String token);

	User findByUserTokenAndAccountLockedAndEnabledAndPasswordExpired(String token, boolean b, boolean c, boolean d);

	User findByMobilePhone(String mobilePhone);

	@Transactional
	@Modifying
	@Query("update User u set u.userToken = null where u.email = :email")
	void setValueForUserToken(@Param("email") String email);

	@Transactional
	@Modifying
	@Query("update User u set u.profileImagePath = ?1 where u.userToken = ?2")
	void setProfileImagePath(String path, String userToken);
}
