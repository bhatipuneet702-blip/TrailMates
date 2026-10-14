package com.tripsync.app.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses",
        foreignKeys = {
                @ForeignKey(entity = Trip.class,
                        parentColumns = "id",
                        childColumns = "tripId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Member.class,
                        parentColumns = "id",
                        childColumns = "payerMemberId",
                        onDelete = ForeignKey.SET_NULL)
        })
public class Expense {
    @PrimaryKey
    @NonNull
    public String id;
    public String tripId;
    public String title;
    public String categoryType; // "food", "transport", "hotel", "fuel", "other"
    public long amountInPaise; // Store as paise (1 rupee = 100 paise)
    public String payerMemberId;
    public long dateMillis;

    public Expense(String id, String tripId, String title, String categoryType,
                   long amountInPaise, String payerMemberId, long dateMillis) {
        this.id = id;
        this.tripId = tripId;
        this.title = title;
        this.categoryType = categoryType;
        this.amountInPaise = amountInPaise;
        this.payerMemberId = payerMemberId;
        this.dateMillis = dateMillis;
    }

    public String getId() {
        return id;
    }

    public String getTripId() {
        return tripId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public long getAmountInPaise() {
        return amountInPaise;
    }

    public String getPayerMemberId() {
        return payerMemberId;
    }

    public long getDateMillis() {
        return dateMillis;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public void setAmountInPaise(long amountInPaise) {
        this.amountInPaise = amountInPaise;
    }

    public void setPayerMemberId(String payerMemberId) {
        this.payerMemberId = payerMemberId;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }
}
