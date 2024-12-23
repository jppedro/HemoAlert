package com.hemoalert.HemoAlert.dto;

import java.io.Serializable;
import java.util.UUID;

public class BloodCenterDTO implements Serializable  {

    private UUID bloodCenterUuid;
    private String nome;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public BloodCenterDTO() {}

    public BloodCenterDTO(UUID bloodCenterUuid, String nome, String rua, String numero, String complemento,
                          String bairro, String cidade, String estado, String cep) {
        this.bloodCenterUuid = bloodCenterUuid;
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public UUID getBloodCenterUuid() {
        return bloodCenterUuid;
    }

    public void setBloodCenterUuid(UUID bloodCenterUuid) {
        this.bloodCenterUuid = bloodCenterUuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "BloodCenter{" + "bloodCenterUuid=" + bloodCenterUuid +
                ", nome=" + nome +
                ", rua=" + rua +
                ", numero=" + numero +
                ", complemento=" + complemento +
                ", bairro=" + bairro +
                ", cidade=" + cidade +
                ", estado=" + estado +
                ", cep=" + cep +
                '}';
    }
}