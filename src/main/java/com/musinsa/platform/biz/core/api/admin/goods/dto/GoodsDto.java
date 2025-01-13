package com.musinsa.platform.biz.core.api.admin.goods.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.platform.biz.core.api.common.dto.CommonDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(callSuper = true)
@Getter
public class GoodsDto extends CommonDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "상품 번호", defaultValue = "0")
    private final Long goodsNo;

    @NotNull(message = "브랜드 정보는 필수입니다.")
    @Schema(description = "브랜드 번호", defaultValue = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long brandNo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "브랜드명", defaultValue = "브랜드")
    private final String brandName;

    @NotNull(message = "카테고리 정보는 필수입니다.")
    @Schema(description = "카테고리 번호", defaultValue = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long categoryNo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "카테고리명", defaultValue = "카테고리")
    private final String categoryName;

    @NotEmpty(message = "상품명은 필수입니다.")
    @Schema(description = "상품명", defaultValue = "상품", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String goodsName;

    @Min(value = 1, message = "상품가격은 0보다 커야합니다.")
    @NotNull(message = "상품가격은 필수입니다.")
    @Schema(description = "상품가격", defaultValue = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long salePrice;

    @Builder
    private GoodsDto(Long goodsNo, Long brandNo, String brandName, Long categoryNo, String categoryName, String goodsName, Long salePrice
            , LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.goodsNo = goodsNo;
        this.brandNo = brandNo;
        this.brandName = brandName;
        this.categoryNo = categoryNo;
        this.categoryName = categoryName;
        this.goodsName = goodsName;
        this.salePrice = salePrice;
    }
}
