package com.ftn.service;

import com.ftn.model.Alarm;
import com.ftn.model.Dijagnoza;
import com.ftn.model.NalogZaAkciju;
import com.ftn.model.Notifikacija;

import java.util.List;

public class RezultatAnalize {

    private Alarm alarm;
    private Dijagnoza dijagnoza;
    private NalogZaAkciju nalogZaAkciju;
    private List<Notifikacija> notifikacije;

    public RezultatAnalize() {
    }

    public RezultatAnalize(Alarm alarm, Dijagnoza dijagnoza, NalogZaAkciju nalogZaAkciju,
            List<Notifikacija> notifikacije) {
        this.alarm = alarm;
        this.dijagnoza = dijagnoza;
        this.nalogZaAkciju = nalogZaAkciju;
        this.notifikacije = notifikacije;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public Dijagnoza getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(Dijagnoza dijagnoza) {
        this.dijagnoza = dijagnoza;
    }

    public NalogZaAkciju getNalogZaAkciju() {
        return nalogZaAkciju;
    }

    public void setNalogZaAkciju(NalogZaAkciju nalogZaAkciju) {
        this.nalogZaAkciju = nalogZaAkciju;
    }

    public List<Notifikacija> getNotifikacije() {
        return notifikacije;
    }

    public void setNotifikacije(List<Notifikacija> notifikacije) {
        this.notifikacije = notifikacije;
    }
}