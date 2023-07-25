package com.zno.heed.MysqlRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zno.heed.MysqlEntites.ZnoReference;
@Repository
public interface ZnoReferenceRepository extends JpaRepository<ZnoReference, Long> {
	
	ZnoReference findByReferenceKeyAndType(String key, String type);
	List<ZnoReference> findByTypeOrderByReferenceKey(String type);
}
