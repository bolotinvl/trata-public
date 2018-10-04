package com.bolotin.trata.data.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "transactions",
        indices = {@Index(value = "id", unique = true), @Index(value = "category_id"), @Index(value = "account_id")},
        foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id"),
                @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "account_id")})
public class Transaction {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "account_id")
    private String accountId;

    @ColumnInfo(name = "category_id")
    private String categoryId;

    private Double value;

    private String date;

    private String note;

    private Boolean active;

    private Boolean synced;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "updated_at")
    private String updatedAt;

    @ColumnInfo(name = "location_latitude")
    private Double locationLatitude;

    @ColumnInfo(name = "location_longtitude")
    private Double locationLongtitude;

    public Transaction(@NonNull String id,
                       String accountId,
                       String categoryId,
                       Double value,
                       String date,
                       String note,
                       Boolean active,
                       Boolean synced,
                       String createdAt,
                       String updatedAt,
                       Double locationLatitude,
                       Double locationLongtitude) {
        this.id = id;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.value = value;
        this.date = date;
        this.note = note;
        this.active = active;
        this.synced = synced;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.locationLatitude = locationLatitude;
        this.locationLongtitude = locationLongtitude;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getSynced() {
        return synced;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public Double getLocationLongtitude() {
        return locationLongtitude;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public void setLocationLongtitude(Double locationLongtitude) {
        this.locationLongtitude = locationLongtitude;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        int prime = 13;
        hash = prime * hash + this.getId().hashCode();
        hash = prime * hash + this.getValue().intValue();
        hash = prime * hash + this.getNote().hashCode();
        hash = prime * hash + this.getCreatedAt().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Transaction other = (Transaction) obj;

        if (!getId().contentEquals(other.getId())) {
            return false;
        }

        if (!getAccountId().contentEquals(other.getAccountId())) {
            return false;
        }

        if (!getCategoryId().contentEquals(other.getCategoryId())) {
            return false;
        }

        if (!getValue().equals(other.getValue())) {
            return false;
        }

        if (!getDate().contentEquals(other.getDate())) {
            return false;
        }

        if (!getNote().contentEquals(other.getNote())) {
            return false;
        }

        if (getActive()!= other.getActive()) {
            return false;
        }

        if (getSynced() != other.getSynced()) {
            return false;
        }

        if (!getCreatedAt().contentEquals(other.getCreatedAt())) {
            return false;
        }

        if (!getUpdatedAt().contentEquals(other.getUpdatedAt())) {
            return false;
        }

        if (getLocationLatitude() != null && other.getLocationLatitude() != null && !getLocationLatitude().equals(other.getLocationLatitude())) {
            return false;
        }

        if (getLocationLongtitude() != null && other.getLocationLongtitude() != null && !getLocationLongtitude().equals(other.getLocationLongtitude())) {
            return false;
        }

        return true;
    }
}
