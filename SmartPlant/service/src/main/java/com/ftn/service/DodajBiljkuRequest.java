package com.ftn.service;

import com.ftn.model.TipBiljke;

public class DodajBiljkuRequest {

    private Long biljkaId;
    private String naziv;
    private TipBiljke tipBiljke;

    public DodajBiljkuRequest() {
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public TipBiljke getTipBiljke() {
        return tipBiljke;
    }

    public void setTipBiljke(TipBiljke tipBiljke) {
        this.tipBiljke = tipBiljke;
    }

    @Override
    public String toString() {
        return "DodajBiljkuRequest{" +
                "biljkaId=" + biljkaId +
                ", naziv='" + naziv + '\'' +
                ", tipBiljke=" + tipBiljke +
                '}';
    }
}