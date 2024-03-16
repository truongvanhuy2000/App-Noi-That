package com.huy.appnoithat.DataModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PricingModelDTO {
    private boolean active;
    private List<PricingModel> pricingModelList;
    @Data @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PricingModel {
        private Integer monthOption;
        private Long price;
        private Integer bonusMonth;
    }
}
