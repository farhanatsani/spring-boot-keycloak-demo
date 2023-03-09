package com.keycloak.demo.campaign;

public class CampaignMapper {
    public static Campaign toCampaign(CampaignDTO campaignDTO) {
        return Campaign.builder()
                .title(campaignDTO.getTitle())
                .description(campaignDTO.getDescription())
                .coupon(campaignDTO.getCoupon())
                .discount(campaignDTO.getDiscount())
                .build();
    }
    public static CampaignDTO toCampaignDTO(Campaign campaign) {
        return CampaignDTO.builder()
                .id(campaign.getId())
                .title(campaign.getTitle())
                .description(campaign.getDescription())
                .coupon(campaign.getCoupon())
                .discount(campaign.getDiscount())
                .build();
    }
}
