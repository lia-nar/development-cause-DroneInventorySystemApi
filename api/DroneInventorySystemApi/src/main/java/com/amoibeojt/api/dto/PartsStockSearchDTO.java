package com.amoibeojt.api.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 検索条件用 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartsStockSearchDTO {
	
    //センターID 複数可
    private List<Integer> centerId;
    //分類ID 複数可
    private List<Integer> categoryId;
    //在庫ID 単一
    private List<Integer> stockIds;
    //部品名パターン
    private String namePattern;
    //在庫数の最小
    private Integer amountMin;
    //在庫数の最大
    private Integer amountMax;
    //更新日時の開始 (yyyy-MM-dd)
    private Instant dateFrom;
    //更新日時の終了 (yyyy-MM-dd)
    private Instant dateTo;
    
}
