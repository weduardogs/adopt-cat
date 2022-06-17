package com.adoptacat.constrains;

public class ConstraintsSearch {

    private long gatoId;
    private String selectedValue;
    private double pesoGato;

    public long getGatoId() {
        return gatoId;
    }

    public void setGatoId(long gatoId) {
        this.gatoId = gatoId;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {

        if (selectedValue.equals("Sí")) {
            selectedValue = "S";
        } else if (selectedValue.equals("No")) {
            selectedValue = "N";
        } else {
            selectedValue = "*";
        }

        this.selectedValue = selectedValue;
    }

    public double getPesoGato() {
        return pesoGato;
    }

    public void setPesoGato(double pesoGato) {
        this.pesoGato = pesoGato;
    }

}
