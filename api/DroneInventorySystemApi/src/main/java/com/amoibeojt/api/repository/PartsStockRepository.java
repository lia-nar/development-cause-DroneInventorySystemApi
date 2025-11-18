package com.amoibeojt.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.amoibeojt.api.entity.PartsStock;

/**
 * 部品在庫テーブルリポジトリー
 *
 * @author	your name
 * 
 */
public interface PartsStockRepository extends JpaRepository<PartsStock, Integer>, JpaSpecificationExecutor<PartsStock> {
	
	// deleteFlag='0' の全件取得
	List<PartsStock> findByDeleteFlag(Boolean deleteFlag);

}
