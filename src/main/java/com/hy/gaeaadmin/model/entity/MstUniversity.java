/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hy.gaeaadmin.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hendryyu
 */
@Entity
@Table(name = "mst_university")
@NamedQueries({
    @NamedQuery(name = "MstUniversity.findAll", query = "SELECT m FROM MstUniversity m")})
public class MstUniversity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "university_name")
    private String universityName;
    @Size(max = 255)
    @Column(name = "university_address1")
    private String universityAddress1;
    @Size(max = 255)
    @Column(name = "university_address2")
    private String universityAddress2;
    @Size(max = 255)
    @Column(name = "university_phone1")
    private String universityPhone1;
    @Size(max = 255)
    @Column(name = "university_phone2")
    private String universityPhone2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enable")
    private int enable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "created_by")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Size(max = 255)
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    @OneToMany(mappedBy = "mstUniversity")
    private List<Sales> salesList;

    public MstUniversity() {
    }

    public MstUniversity(Integer id) {
        this.id = id;
    }

    public MstUniversity(Integer id, String universityName, int enable, String createdBy, Date createdDate) {
        this.id = id;
        this.universityName = universityName;
        this.enable = enable;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityAddress1() {
        return universityAddress1;
    }

    public void setUniversityAddress1(String universityAddress1) {
        this.universityAddress1 = universityAddress1;
    }

    public String getUniversityAddress2() {
        return universityAddress2;
    }

    public void setUniversityAddress2(String universityAddress2) {
        this.universityAddress2 = universityAddress2;
    }

    public String getUniversityPhone1() {
        return universityPhone1;
    }

    public void setUniversityPhone1(String universityPhone1) {
        this.universityPhone1 = universityPhone1;
    }

    public String getUniversityPhone2() {
        return universityPhone2;
    }

    public void setUniversityPhone2(String universityPhone2) {
        this.universityPhone2 = universityPhone2;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Sales> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList) {
        this.salesList = salesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MstUniversity)) {
            return false;
        }
        MstUniversity other = (MstUniversity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hy.gaeaadmin.model.entity.MstUniversity[ id=" + id + " ]";
    }
    
}
