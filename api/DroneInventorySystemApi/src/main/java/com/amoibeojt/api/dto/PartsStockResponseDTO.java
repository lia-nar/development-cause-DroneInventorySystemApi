package com.amoibeojt.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レスポンス用 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartsStockResponseDTO {
    //センターID
    private Integer centerId;
    //分類ID
    private Integer categoryId;
    //在庫ID
    private Integer stockId;
    //部品名
    private String name;
    //在庫数
    private Integer amount;
    //説明
    private String description;
    //登録日時
    private LocalDateTime createDate;
    //更新日時
    private LocalDateTime updateDate;

}
