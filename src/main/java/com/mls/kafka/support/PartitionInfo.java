package com.mls.kafka.support;

/**
 * Created by tangshaoxiong on 16/1/2.
 */
public class PartitionInfo {
    private int brokerId;
    private int partitionId;

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(int partitionId) {
        this.partitionId = partitionId;
    }
}
