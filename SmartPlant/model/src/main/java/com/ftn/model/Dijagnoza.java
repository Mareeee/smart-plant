package com.ftn.model;

public class Dijagnoza {

    private Long biljkaId;
    private Boolean rizikTrulezi;
    private Boolean rizikPepelnice;
    private Boolean rizikCrvenogPauka;
    private Boolean rizikLisnihVasi;
    private Boolean kriticnoStanje;

    public Dijagnoza() {
    }

    public Dijagnoza(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public Long getBiljkaId() {
        return biljkaId;
    }

    public void setBiljkaId(Long biljkaId) {
        this.biljkaId = biljkaId;
    }

    public Boolean getRizikTrulezi() {
        return rizikTrulezi;
    }

    public void setRizikTrulezi(Boolean rizikTrulezi) {
        this.rizikTrulezi = rizikTrulezi;
    }

    public Boolean getRizikPepelnice() {
        return rizikPepelnice;
    }

    public void setRizikPepelnice(Boolean rizikPepelnice) {
        this.rizikPepelnice = rizikPepelnice;
    }

    public Boolean getRizikCrvenogPauka() {
        return rizikCrvenogPauka;
    }

    public void setRizikCrvenogPauka(Boolean rizikCrvenogPauka) {
        this.rizikCrvenogPauka = rizikCrvenogPauka;
    }

    public Boolean getRizikLisnihVasi() {
        return rizikLisnihVasi;
    }

    public void setRizikLisnihVasi(Boolean rizikLisnihVasi) {
        this.rizikLisnihVasi = rizikLisnihVasi;
    }

    public Boolean getKriticnoStanje() {
        return kriticnoStanje;
    }

    public void setKriticnoStanje(Boolean kriticnoStanje) {
        this.kriticnoStanje = kriticnoStanje;
    }

    @Override
    public String toString() {
        return "Dijagnoza{" +
                "biljkaId=" + biljkaId +
                ", rizikTrulezi=" + rizikTrulezi +
                ", rizikPepelnice=" + rizikPepelnice +
                ", rizikCrvenogPauka=" + rizikCrvenogPauka +
                ", rizikLisnihVasi=" + rizikLisnihVasi +
                ", kriticnoStanje=" + kriticnoStanje +
                '}';
    }
}