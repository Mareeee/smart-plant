package com.ftn.model;

public class ParametriBiljke {

    private TipBiljke tipBiljke;
    private double minVlaga;
    private double maxVlaga;
    private double minTemperatura;
    private double minSvetlost;

    public ParametriBiljke() {
    }

    public ParametriBiljke(TipBiljke tipBiljke, double minVlaga, double maxVlaga, double minTemperatura,
            double minSvetlost) {
        this.tipBiljke = tipBiljke;
        this.minVlaga = minVlaga;
        this.maxVlaga = maxVlaga;
        this.minTemperatura = minTemperatura;
        this.minSvetlost = minSvetlost;
    }

    public TipBiljke getTipBiljke() {
        return tipBiljke;
    }

    public void setTipBiljke(TipBiljke tipBiljke) {
        this.tipBiljke = tipBiljke;
    }

    public double getMinVlaga() {
        return minVlaga;
    }

    public void setMinVlaga(double minVlaga) {
        this.minVlaga = minVlaga;
    }

    public double getMaxVlaga() {
        return maxVlaga;
    }

    public void setMaxVlaga(double maxVlaga) {
        this.maxVlaga = maxVlaga;
    }

    public double getMinTemperatura() {
        return minTemperatura;
    }

    public void setMinTemperatura(double minTemperatura) {
        this.minTemperatura = minTemperatura;
    }

    public double getMinSvetlost() {
        return minSvetlost;
    }

    public void setMinSvetlost(double minSvetlost) {
        this.minSvetlost = minSvetlost;
    }

    @Override
    public String toString() {
        return "ParametriBiljke{" +
                "tipBiljke=" + tipBiljke +
                ", minVlaga=" + minVlaga +
                ", maxVlaga=" + maxVlaga +
                ", minTemperatura=" + minTemperatura +
                ", minSvetlost=" + minSvetlost +
                '}';
    }
}