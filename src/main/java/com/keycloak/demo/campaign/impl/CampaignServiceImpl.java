package com.keycloak.demo.campaign.impl;

import com.keycloak.demo.campaign.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;
    @Override
    public CampaignDTO save(CampaignDTO campaignDTO) {
        Campaign campaign = CampaignMapper.toCampaign(campaignDTO);
        campaignRepository.saveAndFlush(campaign);
        return CampaignMapper.toCampaignDTO(campaign);
    }
    @Override
    public List<CampaignDTO> findAll() {
        List<Campaign> campaigns = campaignRepository.findAll();
        return campaigns.stream()
                .map(CampaignMapper::toCampaignDTO)
                .collect(Collectors.toList());
    }
}
