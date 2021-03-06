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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sales")
@NamedQueries({
    @NamedQuery(name = "Sales.findAll", query = "SELECT s FROM Sales s")})
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "trx_num")
    private String trxNum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trx_date")
    @Temporal(TemporalType.DATE)
    private Date trxDate;
    @Column(name = "pod_date_out")
    @Temporal(TemporalType.DATE)
    private Date podDateOut;
    @Column(name = "pod_date_in")
    @Temporal(TemporalType.DATE)
    private Date podDateIn;
    @Size(max = 255)
    @Column(name = "pod_delivery_addr")
    private String podDeliveryAddr;
    @Size(max = 255)
    @Column(name = "occassion_type")
    private String occassionType;
    @Column(name = "event_date")
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    @Size(max = 255)
    @Column(name = "event_location")
    private String eventLocation;
    @Size(max = 255)
    @Column(name = "trx_note")
    private String trxNote;
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
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Customer customer;
    @JoinColumn(name = "sales_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MstSalesType mstSalesType;
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    @ManyToOne
    private MstUniversity mstUniversity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sales")
    private List<SalesDetail> salesDetailList;

    public Sales() {
    }

    public Sales(Integer id) {
        this.id = id;
    }

    public Sales(Integer id, String trxNum, Date trxDate, int enable, String createdBy, Date createdDate) {
        this.id = id;
        this.trxNum = trxNum;
        this.trxDate = trxDate;
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

    public String getTrxNum() {
        return trxNum;
    }

    public void setTrxNum(String trxNum) {
        this.trxNum = trxNum;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public Date getPodDateOut() {
        return podDateOut;
    }

    public void setPodDateOut(Date podDateOut) {
        this.podDateOut = podDateOut;
    }

    public Date getPodDateIn() {
        return podDateIn;
    }

    public void setPodDateIn(Date podDateIn) {
        this.podDateIn = podDateIn;
    }

    public String getPodDeliveryAddr() {
        return podDeliveryAddr;
    }

    public void setPodDeliveryAddr(String podDeliveryAddr) {
        this.podDeliveryAddr = podDeliveryAddr;
    }

    public String getOccassionType() {
        return occassionType;
    }

    public void setOccassionType(String occassionType) {
        this.occassionType = occassionType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getTrxNote() {
        return trxNote;
    }

    public void setTrxNote(String trxNote) {
        this.trxNote = trxNote;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public MstSalesType getMstSalesType() {
        return mstSalesType;
    }

    public void setMstSalesType(MstSalesType mstSalesType) {
        this.mstSalesType = mstSalesType;
    }

    public MstUniversity getMstUniversity() {
        return mstUniversity;
    }

    public void setMstUniversity(MstUniversity mstUniversity) {
        this.mstUniversity = mstUniversity;
    }

    public List<SalesDetail> getSalesDetailList() {
        return salesDetailList;
    }

    public void setSalesDetailList(List<SalesDetail> salesDetailList) {
        this.salesDetailList = salesDetailList;
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
        if (!(object instanceof Sales)) {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hy.gaeaadmin.model.entity.Sales[ id=" + id + " ]";
    }
    
}
