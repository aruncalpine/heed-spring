package com.zno.heed.MysqlRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zno.heed.MysqlEntites.LoginHistory;
@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}