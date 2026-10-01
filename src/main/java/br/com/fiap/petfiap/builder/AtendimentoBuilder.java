package br.com.fiap.petfiap.builder;

import br.com.fiap.petfiap.model.Atendimento;
import java.time.LocalDateTime;

public class AtendimentoBuilder {

    private String tipo;
    private String petNome;
    private String petPorte;
    private String tutorNome;
    private LocalDateTime dataHora;

    public AtendimentoBuilder comTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public AtendimentoBuilder comPet(String petNome, String petPorte) {
        this.petNome = petNome;
        this.petPorte = petPorte;
        return this;
    }

    public AtendimentoBuilder comTutor(String tutorNome) {
        this.tutorNome = tutorNome;
        return this;
    }

    public AtendimentoBuilder comDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        return this;
    }

    public Atendimento construir(Integer id) {
        // Validação do Bug 04: impede atendimento sem nome do pet
        if (this.petNome == null || this.petNome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do pet é obrigatório.");
        }

        //  CORREÇÃO BUG 05: impede construção de atendimento sem porte
        if (this.petPorte == null || this.petPorte.trim().isEmpty()) {
            throw new IllegalArgumentException("Porte do pet é obrigatório.");
        }

        return new Atendimento(id, tipo, petNome, petPorte, tutorNome, dataHora);
    }
}