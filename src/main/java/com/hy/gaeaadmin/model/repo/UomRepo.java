/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.repo;

import com.hy.gaeaadmin.model.entity.MstUom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hendryyu
 */
public interface UomRepo extends JpaRepository<MstUom, Integer> {
    
}
