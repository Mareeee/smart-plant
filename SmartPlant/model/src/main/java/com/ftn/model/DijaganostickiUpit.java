package com.ftn.model;

public class DijaganostickiUpit {

    private Long biljkaId;
    private String simptom;
    private String vizuelniSimptom;
    private long nowMs;

    public DijaganostickiUpit() {
    }

    public DijaganostickiUpit(Long biljkaId, String simptom, long nowMs) {
        this.biljkaId = biljkaId;
        this.simptom = simptom;
        this.nowMs = nowMs;
    }

    public DijaganostickiUpit(Long biljkaId, String simptom, String vizuelniSimptom, long nowMs) {
        this.biljkaId = biljkaId;
        this.simptom = simptom;
        this.vizuelniSimptom = vizuelniSimptom;
        this.nowMs = nowMs;
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public String getSimptom() {
        return simptom;
    }

    public void setSimptom(String simptom) {
        this.simptom = simptom;
    }

    public String getVizuelniSimptom() {
        return vizuelniSimptom;
    }

    public void setVizuelniSimptom(String vizuelniSimptom) {
        this.vizuelniSimptom = vizuelniSimptom;
    }

    public long getNowMs() {
        return nowMs;
    }

    public void setNowMs(long nowMs) {
        this.nowMs = nowMs;
    }

    @Override
    public String toString() {
        return "DijaganostickiUpit{" +
                "biljkaId=" + biljkaId +
                ", simptom='" + simptom + '\'' +
                ", vizuelniSimptom='" + vizuelniSimptom + '\'' +
                ", nowMs=" + nowMs +
                '}';
    }
}