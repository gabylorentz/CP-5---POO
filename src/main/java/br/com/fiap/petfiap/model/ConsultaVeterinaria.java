package br.com.fiap.petfiap.model;

import java.time.LocalDateTime;

public class ConsultaVeterinaria extends Atendimento {

    private String diagnostico;
    private String receita;

    // Construtor completo
    public ConsultaVeterinaria(Integer id, String tipo, String petNome, String petPorte, String tutorNome, LocalDateTime dataHora, String diagnostico, String receita) {
        //  CORREÇÃO BUG 06: repassa petNome e petPorte para a superclasse (Atendimento)
        super(id, tipo, petNome, petPorte, tutorNome, dataHora);
        this.diagnostico = diagnostico;
        this.receita = receita;
    }

    // Construtor simplificado para testes e criação rápida
    public ConsultaVeterinaria(String petNome, String petPorte, String diagnostico) {
        super(null, "CONSULTA", petNome, petPorte, null, LocalDateTime.now());
        this.diagnostico = diagnostico;
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