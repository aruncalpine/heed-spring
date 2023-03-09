package com.zno.heed;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZnoReferenceRepository extends JpaRepository<ZnoReference, Long> {
	
	ZnoReference findByReferenceKeyAndType(String key, String type);
	List<ZnoReference> findByTypeOrderByReferenceKey(String type);
}
