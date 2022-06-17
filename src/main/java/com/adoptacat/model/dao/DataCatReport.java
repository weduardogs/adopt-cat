package com.adoptacat.model.dao;

import java.io.FileInputStream;

public class DataCatReport {

    private String details;
    private FileInputStream foto;
    private byte[] catFoto;

    public DataCatReport(String details, FileInputStream foto) {
        this.setDetails(details);
        this.setFoto(foto);
    }

    public String getDetails() {
        return details;
    }

    private void setDetails(String details) {
        this.details = details;
    }

    public FileInputStream getFoto() {
        return foto;
    }

    private void setFoto(FileInputStream foto) {
        this.foto = foto;
    }
}
