package com.amoibeojt.api.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amoibeojt.api.dto.ApiResponse;
import com.amoibeojt.api.dto.PagedResponse;
import com.amoibeojt.api.dto.PartsStockResponseDTO;
import com.amoibeojt.api.dto.PartsStockSearchDTO;
import com.amoibeojt.api.exception.InvalidInputException;
import com.amoibeojt.api.service.PartsStockService;

import lombok.RequiredArgsConstructor;

/**
 * 部品在庫照会API Controller
 * 
 * @author your name
 *
 * @return ページング結果
 */

@RestController
@RequestMapping("/api/parts/stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PartsStockController {
	
    private final PartsStockService partsStockService;

    @GetMapping
    public ApiResponse<PagedResponse<PartsStockResponseDTO>> search(
        @RequestParam(value="center_id",   required=false) List<Integer> centerId,
        @RequestParam(value="category_id", required=false) List<Integer> categoryId,
        @RequestParam(value="stock_id",    required=false) List<Integer> stockId,
        @RequestParam(value="name_pattern",required=false) String       namePattern,
        @RequestParam(value="amount_min",  required=false) Integer      amountMin,
        @RequestParam(value="amount_max",  required=false) Integer      amountMax,
        @RequestParam(value="date_from",   required=false) Instant      dateFrom,
        @RequestParam(value="date_to",     required=false) Instant      dateTo
    ) {
    	
    	//入力範囲チェック
    	if (amountMin != null && amountMax != null && amountMin > amountMax) {
    		throw new InvalidInputException("amountMinGtMax");
        }
    	
    	if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
    		throw new InvalidInputException("dateFromAfterDateTo");
        }
    	
    	//リクエストパラメータをまとめた検索条件DTOを作成
        PartsStockSearchDTO criteria = new PartsStockSearchDTO(centerId,categoryId,stockId,namePattern,amountMin,amountMax,dateFrom,dateTo);

        //DTOリストを取得
        List<PartsStockResponseDTO> list = partsStockService.search(criteria);
        
        //ページング用DTOに返却データを設定
        PagedResponse<PartsStockResponseDTO> page = new PagedResponse<>(list, list.size());
        
        //取得件数が0件の場合、ダミーメッセージを返却
        if (page.getTotal_count() == 0) {
            PartsStockResponseDTO dummy = PartsStockResponseDTO.builder()
                .name("一致するデータがありませんでした。")
                .build();
            page.setItems(List.of(dummy));
        }

        return new ApiResponse<>(
            "success",
            "部品在庫情報を正常に取得しました",
            page
        );
    }
}