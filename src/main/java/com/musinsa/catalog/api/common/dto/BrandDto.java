package com.musinsa.catalog.api.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString(callSuper = true)
@Getter
@Schema(name = "브랜드")
public class BrandDto extends CommonDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "브랜드 번호")
    private final Long brandNo;

    @NotEmpty(message = "브랜드명은 필수입니다.")
    @Schema(description = "브랜드 명", defaultValue = "브랜드명", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String brandName;

    @Builder
    private BrandDto(Long brandNo, String brandName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.brandNo = brandNo;
        this.brandName = brandName;
    }

}
