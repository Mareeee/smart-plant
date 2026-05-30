package com.ftn.model;

import org.kie.api.definition.type.Position;

public class UzrocnaVeza {

    @Position(0)
    private String uzrok;

    @Position(1)
    private String simptom;

    public UzrocnaVeza(String uzrok, String simptom) {
        this.uzrok = uzrok;
        this.simptom = simptom;
    }

    public String getUzrok() {
        return uzrok;
    }

    public void setUzrok(String uzrok) {
        this.uzrok = uzrok;
    }

    public String getSimptom() {
        return simptom;
    }

    public void setSimptom(String simptom) {
        this.simptom = simptom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UzrocnaVeza that = (UzrocnaVeza) o;
        if (uzrok != null ? !uzrok.equals(that.uzrok) : that.uzrok != null)
            return false;
        return simptom != null ? simptom.equals(that.simptom) : that.simptom == null;
    }

    @Override
    public int hashCode() {
        int result = uzrok != null ? uzrok.hashCode() : 0;
        result = 31 * result + (simptom != null ? simptom.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UzrocnaVeza{uzrok='" + uzrok + "', simptom='" + simptom + "'}";
    }
}