package com.zno.heed.MysqlRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zno.heed.MysqlEntites.ChatUsers;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.chatdata.ChatUsersView;
@Repository
public interface ChatRepository  extends JpaRepository<ChatUsers, Integer>{


	@Query(value="select \n"
			+ "c.id as id,\n "
			+ "	c.date as date,\n"
			+ "	u1.full_name as fullName,\n"
			+ "	u1.mobile_phone as mobilePhone\n"
			+ "from chat_users c \n"
			+ "inner join user u1 on c.dest_user_id = u1.id\n"
		//	+ "inner join user u2 on c.src_user_id = u2.id\n"
			+ "where c.src_user_id=:srcUserId", nativeQuery=true)
	List<ChatUsersView>findDateAndDestUserFields(@Param("srcUserId")Long srcUserId);
	
  ChatUsers   findBySrcUserAndDestUser (User srcUser , User destUser  );
        
     boolean existsChatUsersBySrcUserIdAndDestUserId (User srcUser , User destUser  );
     
     @Query(value = "SELECT * FROM chat_users WHERE id = ?1",  nativeQuery = true)
       ChatUsers findById(Long id);
     
     @Query(value="select \n"
 			+ "	u1.mobile_phone as mobilePhone\n"
 			+ "from chat_users c \n"
 			+ "inner join user u1 on c.dest_user_id = u1.id\n"
 			+ "where c.id=:id", nativeQuery=true)
     String findDestUserMobilePhoneById(@Param("id") Long id);
}
