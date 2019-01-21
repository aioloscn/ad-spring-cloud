package com.aiolos.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Aiolos
 * @date 2019-01-21 13:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_keywork")
public class AdUnitKeywork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Column(name = "keywork", nullable = false)
    private String keywork;

    public AdUnitKeywork(Long unitId, String keywork) {
        this.unitId = unitId;
        this.keywork = keywork;
    }
}
