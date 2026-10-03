package br.com.fiap.petfiap.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaVeterinariaTest {

    @Test
    @DisplayName(" Bug 06: Deve guardar e retornar corretamente os dados do pet na ConsultaVeterinaria")
    public void devePreencherDadosDoPetNaConsulta() {
        // Arrange & Act: instancia a consulta passando o nome "Mimi"
        ConsultaVeterinaria consulta = new ConsultaVeterinaria("Mimi", "PEQUENO", "Exame de rotina");

        // Assert: verifica se o nome retornado é "Mimi" e não null
        assertNotNull(consulta.getPetNome(), "O nome do pet não deveria ser null");
        assertEquals("Mimi", consulta.getPetNome());
        assertEquals("PEQUENO", consulta.getPetPorte());
    }

    @Test
    @DisplayName("🧪 Teste 03: Deve instanciar ConsultaVeterinaria completa com diagnostico e receita")
    public void deveCriarConsultaComDiagnosticoEReceita() {
        LocalDateTime agora = LocalDateTime.now();
        ConsultaVeterinaria consulta = new ConsultaVeterinaria(
                1, "CONSULTA", "Mimi", "PEQUENO", "Carlos", agora, "Otite", "Pingar gotas 2x ao dia"
        );

        assertEquals(1, consulta.getProtocolo());
        assertEquals("CONSULTA", consulta.getTipo());
        assertEquals("Mimi", consulta.getPetNome());
        assertEquals("Otite", consulta.getDiagnostico());
        assertEquals("Pingar gotas 2x ao dia", consulta.getReceita());
    }
}