package com.bolotin.trata.data.db.model;

public class TransactionGeneralItem extends TransactionListItem {

    private Transaction transaction;

    private String value;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }

    @Override
    public int hashCode() {
        return transaction.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final TransactionGeneralItem other = (TransactionGeneralItem) obj;

        return transaction.equals(other.getTransaction());
    }
}
