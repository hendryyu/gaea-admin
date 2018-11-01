/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.repo;

import com.hy.gaeaadmin.model.entity.MstCity;
import com.hy.gaeaadmin.model.inter.QueryPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hendryyu
 */
public interface CityRepo extends JpaRepository<MstCity, Integer>, QueryPredicateExecutor<MstCity> {
    
}
