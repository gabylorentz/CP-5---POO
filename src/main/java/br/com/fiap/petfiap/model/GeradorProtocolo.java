package br.com.fiap.petfiap.model;

// Padrao Singleton (Aula 14): garante uma unica instancia na aplicacao.
// Responsavel pela geracao sequencial dos protocolos dos atendimentos.
// Mantem a numeracao global dos protocolos do sistema.

public class GeradorProtocolo {

    private static GeradorProtocolo instancia;

    private int contador;

    private GeradorProtocolo() {
        contador = 0;
        System.out.println("GeradorProtocolo criado!");
    }

    public static GeradorProtocolo getInstancia() {
        if (instancia == null) {
            instancia = new GeradorProtocolo();
        }
        return instancia;
    }

    public int proximo() {
        contador++;
        return contador;
    }
}