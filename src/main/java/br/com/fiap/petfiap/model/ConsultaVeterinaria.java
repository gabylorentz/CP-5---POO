package br.com.fiap.petfiap.model;

import java.time.LocalDateTime;

public class ConsultaVeterinaria extends Atendimento {

    private String diagnostico;
    private String receita;

    public ConsultaVeterinaria(Integer id,
                               String tipo,
                               String petNome,
                               String petPorte,
                               String tutorNome,
                               LocalDateTime dataHora,
                               String diagnostico,
                               String receita) {

        super(id, petNome, petPorte, tutorNome, dataHora);

        this.diagnostico = diagnostico;
        this.receita = receita;
    }

    public ConsultaVeterinaria(String petNome,
                               String petPorte,
                               String diagnostico) {

        super(0, petNome, petPorte, null, LocalDateTime.now());

        this.diagnostico = diagnostico;
    }

    @Override
    public String getTipo() {
        return "CONSULTA";
    }

    @Override
    public double calcularPreco() {
        return 150.0;
    }

    @Override
    public int calcularPontosFidelidade() {
        return 50;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }
}