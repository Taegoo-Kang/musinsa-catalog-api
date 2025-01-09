package com.musinsa.catalog.api.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Schema(name = "상품")
public class GoodsDto extends CommonDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "상품 번호")
    private final Long goodsNo;

    @NotNull(message = "브랜드 정보는 필수입니다.")
    @Schema(description = "브랜드 번호", defaultValue = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long brandNo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "브랜드 명")
    private final String brandName;

    @NotNull(message = "카테고리 정보는 필수입니다.")
    @Schema(description = "카테고리 번호", defaultValue = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Long categoryNo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "카테고리 명")
    private final String categoryName;

    @NotEmpty(message = "상품명은 필수입니다.")
    @Schema(description = "상품 명", defaultValue = "상품명", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String goodsName;

    @Min(1)
    @NotNull(message = "판매가격은 필수입니다.")
    @Schema(description = "판매 가격", defaultValue = "0", requiredMode = Schema.RequiredMode.REQUIRED)
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
