package com.ftn.model;

public class IstorijaZalivanja {

    private Long biljkaId;
    private long timestampMs;

    public IstorijaZalivanja() {
    }

    public IstorijaZalivanja(Long biljkaId, long timestampMs) {
        this.biljkaId = biljkaId;
        this.timestampMs = timestampMs;
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public long getTimestampMs() {
        return timestampMs;
    }

    public void setTimestampMs(long timestampMs) {
        this.timestampMs = timestampMs;
    }

    @Override
    public String toString() {
        return "IstorijaZalivanja{" +
                "biljkaId=" + biljkaId +
                ", timestampMs=" + timestampMs +
                '}';
    }
}