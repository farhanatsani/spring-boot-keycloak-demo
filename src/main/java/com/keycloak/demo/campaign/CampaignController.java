package com.keycloak.demo.campaign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignServiceImpl;

    @GetMapping
    public ResponseEntity<?> getCampaigns() {

        List<CampaignDTO> campaigns = campaignServiceImpl.findAll();

        return ResponseEntity.ok(campaigns);
    }

    // https://www.baeldung.com/spring-security-expressions

    @PostMapping
//    @PreAuthorize("hasRole('MARKETING')")
    @PreAuthorize("hasAuthority('ROLE_MARKETING')")
    public ResponseEntity<?> saveProduct(@RequestBody CampaignDTO campaignRequest) {

        CampaignDTO campaign = campaignServiceImpl.save(campaignRequest);

        return ResponseEntity.ok(campaign);
    }

}
