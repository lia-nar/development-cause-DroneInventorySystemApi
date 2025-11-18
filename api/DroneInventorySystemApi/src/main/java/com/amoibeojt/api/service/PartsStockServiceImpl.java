package com.amoibeojt.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amoibeojt.api.dto.PartsStockResponseDTO;
import com.amoibeojt.api.dto.PartsStockSearchDTO;
import com.amoibeojt.api.entity.PartsStock;
import com.amoibeojt.api.repository.PartsStockRepository;

import lombok.RequiredArgsConstructor;

/**
 * 部品在庫照会のサービスの実装クラス（DTO にマッピング）
 * 
 * @author your name
 */

@Service
@RequiredArgsConstructor
public class PartsStockServiceImpl implements PartsStockService {

    private final PartsStockRepository repository;

    @Override
    public List<PartsStockResponseDTO> search(PartsStockSearchDTO c) {
        Specification<PartsStock> spec = buildSpecification(c);
        return repository.findAll(spec).stream()
            .map(e -> PartsStockResponseDTO.builder()
                .centerId(e.getCenterId())
                .categoryId(e.getCategoryId())
                .stockId(e.getStockId())
                .name(e.getName())
                .amount(e.getAmount())
                .description(e.getDescription())
                .createDate(e.getCreateDate())
                .updateDate(e.getUpdateDate())
                .build()
            )
            .collect(Collectors.toList());
    }

    /**
     * 検索条件からSpecificationを組み立て
     */
    private Specification<PartsStock> buildSpecification(PartsStockSearchDTO c) {
    	Specification<PartsStock> spec = Specification.where((root, q, cb) ->
        cb.isFalse(root.get("deleteFlag"))  // ps.delete_flag = false
    );

    // center_id IN (…)
    if (!CollectionUtils.isEmpty(c.getCenterId())) {
        spec = spec.and((root, q, cb) ->
            root.get("centerId").in(c.getCenterId()));
    }
    // category_id IN (…)
    if (!CollectionUtils.isEmpty(c.getCategoryId())) {
        spec = spec.and((root, q, cb) ->
            root.get("categoryId").in(c.getCategoryId()));
    }
    // stock_id IN (…)
    if (!CollectionUtils.isEmpty(c.getStockIds())) {
        spec = spec.and((root, q, cb) ->
            root.get("stockId").in(c.getStockIds()));
    }

    // 部品名部分一致 (case‐insensitive)
    if (StringUtils.hasText(c.getNamePattern())) {
        spec = spec.and((root, q, cb) ->
            cb.like(cb.lower(root.get("name")),
                    "%" + c.getNamePattern().toLowerCase() + "%"));
    }

    // 数値範囲
    if (c.getAmountMin() != null) {
        spec = spec.and((root, q, cb) ->
            cb.ge(root.get("amount"), c.getAmountMin()));
    }
    if (c.getAmountMax() != null) {
        spec = spec.and((root, q, cb) ->
            cb.le(root.get("amount"), c.getAmountMax()));
    }

    // 日時範囲 (Instant を LocalDateTime に変換)
    if (c.getDateFrom() != null) {
        // Instant → LocalDateTime に変換してから比較
        LocalDateTime from = LocalDateTime.ofInstant(c.getDateFrom(), ZoneOffset.UTC);
        spec = spec.and((root, q, cb) ->
            cb.greaterThanOrEqualTo(root.get("updateDate"), from));
    }

    if (c.getDateTo() != null) {
        LocalDateTime to = LocalDateTime.ofInstant(c.getDateTo(), ZoneOffset.UTC);
        spec = spec.and((root, q, cb) ->
            cb.lessThanOrEqualTo(root.get("updateDate"), to));
    }
        return spec;
    }
}
