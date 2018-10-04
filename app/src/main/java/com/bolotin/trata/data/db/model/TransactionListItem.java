package com.bolotin.trata.data.db.model;

public abstract class TransactionListItem {

    static final int TYPE_HEADER = 1;
    static final int TYPE_GENERAL = 0;

    abstract public int getType();
}
