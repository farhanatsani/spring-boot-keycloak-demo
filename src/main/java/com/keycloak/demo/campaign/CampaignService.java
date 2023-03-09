package com.keycloak.demo.campaign;

import java.util.List;

public interface CampaignService {
    CampaignDTO save(CampaignDTO campaignDTO);
    List<CampaignDTO> findAll();
}
