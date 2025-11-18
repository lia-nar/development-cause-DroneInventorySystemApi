package com.amoibeojt.api.service;

import java.util.List;

import com.amoibeojt.api.dto.PartsStockResponseDTO;
import com.amoibeojt.api.dto.PartsStockSearchDTO;

/**
 * 部品在庫照会のサービスのインターフェース
 * 
 * @author your name
 */

public interface  PartsStockService {
    /**
     * 検索条件に合致する在庫情報を取得
     * @param search 検索条件DTO
     * @return 条件に合致した在庫一覧
     */
    List<PartsStockResponseDTO> search(PartsStockSearchDTO search);

}
