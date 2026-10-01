# Checkpoint 5 — Bug Hunt PetFiap

> Copie este arquivo para a raiz do seu repositório com o nome **README.md**
> e preencha todas as seções.

## Identificação

**Grupo:** ___

| Integrante | RM | Turma |
|---|---|---|
| Julia Aparicio | 563623 | 2CCPO |
| Gabrielly Lorentz | 565806 | 2CCPO |
| Giovana Praieiro | 565681 | 2CCPO |
| Heitor Barbosa | 563078 | 2CCPO |
| Maria Eduarda | 565386 | 2CCPO |
| Nicole Calasans | 564381 | 2CCPO |

| Campo | |
|---|---|
| **Total de bugs corrigidos** | ___ / 12 |
| **Total de ajustes de Clean Code** | ___ / 6 |
| **Total de testes novos escritos** | ___ / 6 |
| **Suíte final (Run As → JUnit Test)** | ___ testes, ___ falhas |

---

## Parte 1 — Bugs encontrados

> Uma linha por bug, na ordem em que você os encontrou. Use a numeração dos seus
> commits (`fix: bug01 ...`). Preencha TODAS as colunas — metade da nota está aqui.

| # | Sintoma observado (o que fiz/vi) | Causa raiz (arquivo e linha aproximada) | Correção aplicada | Conceito da disciplina |
|---|---|---|---|---|
| bug01 | O novo teste de preço esperava 60.0 para banho de porte PEQUENO, mas recebeu 100.0. | Banho.java, método calcularPreco(). Os valores dos portes PEQUENO e GRANDE estavam invertidos. | Banho.java, método calcularPreco(). Os valores dos portes PEQUENO e GRANDE estavam invertidos. | Polimorfismo, sobrescrita de método e aplicação de regra de negócio. |
| bug02 | O teste deveCriarTosaQuandoTipoForTosa esperava uma instância de Tosa, mas recebeu uma instância de Banho. | AtendimentoFactory.java, método criar(). O caso "TOSA" construía new Banho(...). | A criação foi alterada para new Tosa(...). | Padrão Factory, polimorfismo e instanciação de subclasses. |
| bug03 | Escrevi o teste TosaTest.deveDurar60MinutosQuandoForTosa e ele falhou com expected: <60> but was: <30>. | Tosa.java, método getDuracaoMinutos(String porte), linha ~38. A assinatura tinha um parâmetro que o método da classe pai Atendimento não tem, então era uma sobrecarga, e não uma sobrescrita. O Java executava o método padrão do pai, que retorna 30. | Removi o parâmetro String porte, deixando a assinatura igual à da classe pai, e adicionei @Override. | Polimorfismo, herança, sobrescrita (override) vs sobrecarga (overload). |
| bug04 | O teste AtendimentoBuilderTest.deveMontarAtendimentoCompleto falhou com expected: <Rex> but was: <null>. | AtendimentoBuilder.java, método comPet(), linha ~24. A instrução petNome = petNome; atribuía o parâmetro a ele mesmo, porque o parâmetro tinha o mesmo nome do atributo e faltava o this. O atributo da classe ficava null. | Troquei para this.petNome = petNome;, fazendo o valor ser guardado no atributo do Builder. | Encapsulamento, uso do this, sombreamento (shadowing) de atributo por parâmetro, padrão Builder. |
| bug05 | Ao tentar construir um atendimento sem informar o porte do pet, o builder permitia a criação de um objeto incompleto sem lançar exceção. | AtendimentoBuilder.java, método construir(), linha ~35. Faltava a validação do atributo petPorte antes de instanciar o Atendimento. | Adicionada a validação if (this.petPorte == null \|\| this.petPorte.trim().isEmpty()) lançando IllegalArgumentException. | Padrão Builder, encapsulamento e validação de invariantes. |
| bug06 | Ao instanciar um objeto ConsultaVeterinaria, os métodos para obter o nome e porte do pet retornavam null mesmo passando os dados no construtor. | ConsultaVeterinaria.java, construtor, linha ~12. O construtor da classe filha não repassava os parâmetros de pet para a classe pai Atendimento via super(). | Atualizada a chamada do construtor da superclasse passando petNome e petPorte recebidos na consulta. | Herança, construtores de subclasses e repasse de estado via super(). |
| bug07 | | | | |
| bug08 | | | | |
| bug09 | | | | |
| bug10 | | | | |
| bug11 | | | | |
| bug12 | | | | |

---

## Parte 2 — Ajustes de Clean Code

| # | Onde estava | Qual princípio/boas práticas era violado | O que eu mudei |
|---|---|---|---|
| clean01 | AtendimentoFactory.java, método criar() | Nomes pouco significativos e baixa legibilidade. | Os parâmetros p, t, n, po, tu e d foram renomeados para protocolo, tipo, petNome, petPorte, tutorNome e dataHora. |
| clean02 | Tosa.java, métodos calcularPreco(), calcularPontosFidelidade() e getDuracaoMinutos() | Números mágicos: os valores 70.0, 90.0, 120.0, 30 e 60 estavam soltos no código, sem nome que explicasse o significado. | Criei as constantes PRECO_PORTE_PEQUENO, PRECO_PORTE_MEDIO, PRECO_PORTE_GRANDE, PONTOS_FIDELIDADE e DURACAO_MINUTOS e passei a usá-las nos métodos, sem alterar o comportamento. |
| clean03 | ConsultaVeterinaria / AtendimentoBuilder | Padronização dos construtores da ConsultaVeterinaria para reaproveitar o estado da superclasse via `super()`, além de simplificar a lógica de validação no `AtendimentoBuilder`. | Encapsulamento, DRY (Don't Repeat Yourself) e Princípio da Responsabilidade Única (SRP). |
| clean04 | | | |
| clean05 | | | |
| clean06 | | | |

---

## Parte 3 — Testes novos (regras que estavam sem cobertura)

> Uma linha por teste novo (`test: ...`). "Regra coberta" é o comportamento do
> contrato (seção 3 do enunciado) que o teste protege. Em "Resultado", diga se o
> teste ficou vermelho ao ser escrito (revelou bug — qual?) ou verde de cara
> (regra já estava correta).

| # | Teste escrito (classe.método) | Regra coberta | Resultado ao escrever (vermelho/verde) |
|---|---|---|---|
| teste01 | BanhoTest.deveCalcularPrecoCorretoQuandoPorteVariar | O preço do banho deve ser R$ 60 para PEQUENO, R$ 80 para MEDIO e R$ 100 para GRANDE. | Vermelho ao ser escrito. Revelou o bug01, pois PEQUENO retornava 100.0 em vez de 60.0. |
| teste02 | TosaTest.deveDurar60MinutosQuandoForTosa | A tosa deve durar 60 minutos. | Vermelho ao ser escrito. Revelou o bug03, pois getDuracaoMinutos() retornava 30 (valor padrão da classe Atendimento) em vez de 60. |
| teste03 | ConsultaVeterinariaTest.deveCriarConsultaComDiagnosticoEReceita | Validação do preenchimento completo dos dados do pet e dos campos de diagnóstico e receita na ConsultaVeterinaria. | Verde ao ser escrito. Validou o comportamento correto após as correções. |
| teste04 | | | |
| teste05 | | | |
| teste06 | | | |

---

## Parte 4 — Perguntas de reflexão

> Responda com suas palavras, 5 a 10 linhas cada, **usando o código real do
> projeto como exemplo**. Respostas genéricas de tutorial não pontuam.

### 1. A suíte como contrato (Aula 15)
O projeto chegou com 20 testes, 9 vermelhos. Descreva como você usou as
mensagens de falha (ex.: `expected: <Rex> but was: <null>`) para caçar os bugs.
O que a suíte de testes tem de melhor do que testar tudo na mão com curl?

### 2. Mock e injeção de dependência (Aulas 13 a 15)
No `AgendaServiceTest`, o `@Mock` cria um `AtendimentoRepository` falso e o
`@InjectMocks` o injeta no service. Explique a relação disso com o `@Autowired`
que o Spring faz em produção — quem "injeta" em cada mundo, e por que o teste
consegue rodar sem banco e sem subir o Spring?

### 3. `==` vs `.equals()` (Aula 7)
Um dos bugs fazia o agendamento duplicado passar pela verificação de conflito.
Explique por que `==` entre Strings e `LocalDateTime` falhou aqui, por que ele
"funciona por sorte" com literais como `"Rex"`, e o que a sua correção mudou.

### 4. Sobrescrita vs sobrecarga (Aula 7)
Na classe Atendimento, o método getDuracaoMinutos() não recebe parâmetros e retorna 30 como padrão. A Tosa deveria sobrescrevê-lo para retornar 60, mas foi declarada como getDuracaoMinutos(String porte). Como a lista de parâmetros era diferente, o Java entendeu como uma sobrecarga (overload): um método novo, com outra assinatura, que convivia com o herdado. Já a sobrescrita (override) exige a mesma assinatura do método da classe pai, e é ela que ativa o polimorfismo. Por isso, quando o código chamava tosa.getDuracaoMinutos(), o método executado era o da classe pai, e a duração saía 30. O código compilava normalmente porque sobrecarga é válida em Java, e os testes originais não verificavam a duração. Só o teste novo deveDurar60MinutosQuandoForTosa revelou o problema. Se o método tivesse @Override, o compilador daria erro na hora, porque não existe nenhum getDuracaoMinutos(String) na classe pai para ser sobrescrito. A classe Banho já usava @Override e funcionava corretamente, o que mostra a diferença entre as duas.

### 5. Singleton manual vs bean do Spring (Aula 14)
O `GeradorProtocolo` é um Singleton escrito à mão e causou um dos bugs.
Explique o que ele garante, qual foi o bug, e por que o `AgendaService`
(`@Service`) não corre o mesmo risco no container do Spring?

### 6. Cobertura de testes: onde parar? (Aula 15)
Dos 6 testes novos que você escreveu, alguns ficaram vermelhos (revelaram
bugs) e outros verdes de cara (regras já corretas). Vale a pena manter os que
ficaram verdes? Em um projeto real com prazo, o que você priorizaria testar:
caminho feliz, caminhos de erro, ou 100% de cobertura? Justifique.

---

## Parte 5 — Espaço livre (opcional)

Alguma dificuldade, dúvida ou comentário sobre o checkpoint?