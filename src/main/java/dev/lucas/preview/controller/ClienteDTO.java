package dev.lucas.preview.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.TimeZone;

public class ClienteDTO {

    private static final SimpleDateFormat formatadorDeData
            = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String criadoEm;

    public java.util.Date getCriadoEmConvertido(String timezone) throws java.text.ParseException {
        formatadorDeData.setTimeZone(TimeZone.getTimeZone(timezone));
        return formatadorDeData.parse(this.criadoEm);
    }

    public void setCriadoEm(java.util.Date criadoEm, String timezone) {
        formatadorDeData.setTimeZone(TimeZone.getTimeZone(timezone));
        this.criadoEm = formatadorDeData.format(criadoEm);
    }
}
