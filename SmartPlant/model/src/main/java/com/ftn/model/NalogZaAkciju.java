package com.ftn.model;

public class NalogZaAkciju {

    private Long biljkaId;
    private Boolean pokretanjePumpe;
    private Boolean paljenjeGrejalice;
    private Boolean paljenjeUvLampe;
    private Boolean blokiranoZalivanje;

    public NalogZaAkciju() {
    }

    public NalogZaAkciju(Long biljkaId) {
        this.biljkaId = biljkaId;
        this.pokretanjePumpe = false;
        this.paljenjeGrejalice = false;
        this.paljenjeUvLampe = false;
        this.blokiranoZalivanje = false;
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public Boolean getPokretanjePumpe() {
        return pokretanjePumpe;
    }

    public void setPokretanjePumpe(Boolean pokretanjePumpe) {
        this.pokretanjePumpe = pokretanjePumpe;
    }

    public Boolean getPaljenjeGrejalice() {
        return paljenjeGrejalice;
    }

    public void setPaljenjeGrejalice(Boolean paljenjeGrejalice) {
        this.paljenjeGrejalice = paljenjeGrejalice;
    }

    public Boolean getPaljenjeUvLampe() {
        return paljenjeUvLampe;
    }

    public void setPaljenjeUvLampe(Boolean paljenjeUvLampe) {
        this.paljenjeUvLampe = paljenjeUvLampe;
    }

    public Boolean getBlokiranoZalivanje() {
        return blokiranoZalivanje;
    }

    public void setBlokiranoZalivanje(Boolean blokiranoZalivanje) {
        this.blokiranoZalivanje = blokiranoZalivanje;
    }

    @Override
    public String toString() {
        return "NalogZaAkciju{" +
                "biljkaId=" + biljkaId +
                ", pokretanjePumpe=" + pokretanjePumpe +
                ", paljenjeGrejalice=" + paljenjeGrejalice +
                ", paljenjeUvLampe=" + paljenjeUvLampe +
                ", blokiranoZalivanje=" + blokiranoZalivanje +
                '}';
    }
}