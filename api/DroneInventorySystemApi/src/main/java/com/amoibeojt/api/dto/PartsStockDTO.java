package com.amoibeojt.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部品在庫照会のDTO
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartsStockDTO {
	
	// センターID
	private Integer centerId;
    // 分類ID
	private Integer categoryId;
	//在庫ID
	private Integer stockId;
	//部品名
	private String namePattern;
	//在庫数量の最小値
	private Integer amountMin;
	//在庫数量の最大値
	private Integer amountMax;
	//更新日時の開始日
	private String dateFrom;
	//更新日時の終了日
	private String dateTo;

}
