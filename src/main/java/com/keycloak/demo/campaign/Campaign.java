package com.keycloak.demo.campaign;

import com.keycloak.demo.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "campaign")
@Getter @Setter @SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign extends BaseEntity {
    private String title;
    private String description;
    private String coupon;
    private BigDecimal discount;
}
