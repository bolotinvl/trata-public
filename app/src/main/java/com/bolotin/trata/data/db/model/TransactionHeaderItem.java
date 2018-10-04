package com.bolotin.trata.data.db.model;

public class TransactionHeaderItem extends TransactionListItem {

    private String date;

    private String totalAmount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        int prime = 13;
        hash = prime * hash + this.getDate().hashCode();
        hash = prime * hash + this.getTotalAmount().hashCode();
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

        final TransactionHeaderItem other = (TransactionHeaderItem) obj;

        if (!getDate().contentEquals(other.getDate())) {
            return false;
        }

        if (!getTotalAmount().contentEquals(other.getTotalAmount())) {
            return false;
        }

        return true;
    }
}
