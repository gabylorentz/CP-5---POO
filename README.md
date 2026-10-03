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
| **Total de bugs corrigidos** | 12 / 12 |
| **Total de ajustes de Clean Code** | 6 / 6 |
| **Total de testes novos escritos** | 6 / 6 |
| **Suíte final (Run As → JUnit Test)** | 21 testes, 0 falhas |
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
| bug07 | O gerador de protocolos podia criar mais de uma instância, quebrando o comportamento esperado do Singleton. | GeradorProtocolo.java, método getInstancia(). A lógica de controle da instância única estava incorreta. | Ajustado o método getInstancia() para garantir a reutilização da mesma instância do GeradorProtocolo. | Padrão Singleton, encapsulamento e gerenciamento de estado global. |
| bug08 | Era possível criar um atendimento sem informar o nome do pet. | AtendimentoBuilder.java, método construir(), linha ~35. Não existia validação para o atributo petNome antes da criação do atendimento. | Adicionada validação para impedir nome nulo ou vazio, lançando IllegalArgumentException. | Padrão Builder, validação de invariantes e programação defensiva. |
| bug09 | Ao tentar agendar um atendimento para o mesmo pet no mesmo horário de um atendimento já agendado, o sistema gerava NullPointerException em vez de HorarioOcupadoException. | AgendaService.java, método agendar(): as comparações de petNome e dataHora utilizavam ==, comparando referências em vez dos valores. | As comparações foram alteradas para equals(), permitindo identificar corretamente o conflito e lançar HorarioOcupadoException. | Comparação de objetos e Strings, tratamento de exceções e regras de negócio. |
| bug10 | Ao buscar um atendimento por um ID inexistente, o sistema retornava null em vez de AtendimentoNaoEncontradoException. | AgendaService.java, método buscarPorId(): o catch (Exception) capturava a exceção e retornava null. | O try/catch genérico foi removido, permitindo que o orElseThrow() lance corretamente AtendimentoNaoEncontradoException. | Optional, tratamento de exceções e programação defensiva. |
| bug11 | Ao tentar cancelar um atendimento já concluído ou já cancelado, a operação podia alterar o status para CANCELADO novamente, pois o método cancelar() não validava o status atual. | Atendimento.java, método cancelar(). O método apenas atribuía CANCELADO sem verificar se o atendimento estava em AGENDADO. | Adicionada validação para permitir o cancelamento somente quando o status for AGENDADO; caso contrário, é lançada StatusInvalidoException. | Regras de negócio, transição de estados/status e validação de invariantes. |
| bug12 | Era possível tentar agendar um atendimento com data/hora no passado, e a validação ocorria somente depois da consulta ao repositório. | AgendaService.java, método agendar(). Faltava validar dataHora antes de executar repository.findByPetNome(). | Adicionada validação de data/hora no início de agendar(), lançando IllegalArgumentException antes de consultar o repositório. | Validação antecipada (fail fast), programação defensiva e regras de negócio. |
---
## Parte 2 — Ajustes de Clean Code
| # | Onde estava | Qual princípio/boas práticas era violado | O que eu mudei |
|---|---|---|---|
| clean01 | AtendimentoFactory.java, método criar() | Nomes pouco significativos e baixa legibilidade. | Os parâmetros p, t, n, po, tu e d foram renomeados para protocolo, tipo, petNome, petPorte, tutorNome e dataHora. |
| clean02 | Tosa.java, métodos calcularPreco(), calcularPontosFidelidade() e getDuracaoMinutos() | Números mágicos: os valores 70.0, 90.0, 120.0, 30 e 60 estavam soltos no código, sem nome que explicasse o significado. | Criei as constantes PRECO_PORTE_PEQUENO, PRECO_PORTE_MEDIO, PRECO_PORTE_GRANDE, PONTOS_FIDELIDADE e DURACAO_MINUTOS e passei a usá-las nos métodos, sem alterar o comportamento. |
| clean03 | ConsultaVeterinaria / AtendimentoBuilder | Padronização dos construtores da ConsultaVeterinaria para reaproveitar o estado da superclasse via `super()`, além de simplificar a lógica de validação no `AtendimentoBuilder`. | Encapsulamento, DRY (Don't Repeat Yourself) e Princípio da Responsabilidade Única (SRP). |
| clean04 | AtendimentoBuilder.java, método construir() | Método concentrava a validação e a construção do objeto, reduzindo a legibilidade. | Extraí as validações para o método validarCamposObrigatorios(), deixando o método construir() mais simples e organizado. |
| clean05 | AgendaService.java, injeção do AtendimentoRepository | A injeção da dependência diretamente no atributo deixava a dependência menos explícita e dificultava a testabilidade da classe. | Substituída a injeção por atributo com @Autowired pela injeção via construtor, tornando o AtendimentoRepository uma dependência explícita do AgendaService e facilitando os testes unitários com Mockito. |
| clean06 | AgendaService.java, método agendar(), na validação da data/hora | A validação estava diretamente dentro do método agendar(), deixando a leitura do fluxo principal menos clara e misturando a validação com a lógica de agendamento. | Extraí a validação para o método privado validarDataHora(Atendimento), mantendo o mesmo comportamento e garantindo que ela continue antes da consulta ao repository. |
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
| teste03 | ConsultaVeterinariaTest.deveCriarConsultaComDiagnosticoEReceita | Validação do preenchimento completo dos dados do pet e dos campos de diagnóstico e receita na ConsultaVeterinaria. | Vermelho antes da correção final. A asserção verificava getId(), mas o primeiro parâmetro do construtor é tratado como protocolo; após a correção para getProtocolo(), o teste passou. |
| teste04 | AtendimentoBuilderTest.deveRecusarMontagemComNomeDoPetVazio | Um atendimento não pode ser criado com nome de pet vazio. | Verde após as correções. Confirmou o comportamento esperado da validação adicionada no Builder. |
| teste05 | AtendimentoBuilderTest.deveCancelarAtendimentoAgendado | Um atendimento com status AGENDADO pode ser cancelado, passando para CANCELADO, e a alteração deve ser persistida pelo repository.save(). | Verde ao ser escrito. A regra já estava correta e o teste passou a protegê-la contra regressões futuras. |
| teste06 | AtendimentoBuilderTest.deveRecusarCancelamentoDeAtendimentoJaConcluido | Somente atendimentos com status AGENDADO podem ser cancelados; um atendimento CONCLUIDO deve lançar StatusInvalidoException e não deve ser salvo. | Verde ao ser escrito, após a correção do bug11. O teste passou e também verifica com Mockito que repository.save() não é chamado quando o cancelamento é recusado. |
---
## Parte 4 — Perguntas de reflexão
> Responda com suas palavras, 5 a 10 linhas cada, **usando o código real do
> projeto como exemplo**. Respostas genéricas de tutorial não pontuam.
### 1. A suíte como contrato (Aula 15)
O projeto chegou com 20 testes, 9 vermelhos. Descreva como você usou as
mensagens de falha (ex.: expected: <Rex> but was: <null>) para caçar os bugs.
O que a suíte de testes tem de melhor do que testar tudo na mão com curl?
Resposta: Usei as mensagens dos testes como um ponto de partida para localizar a causa, em vez de tentar descobrir os problemas somente olhando o código. Por exemplo, expected: <Rex> but was: <null> indicou que um valor recebido pelo AtendimentoBuilder não estava sendo armazenado corretamente. Nos bugs de preço e duração, a diferença entre o valor esperado e o valor retornado mostrou qual regra estava sendo violada. No caso da ConsultaVeterinaria, a falha também ajudou a perceber que o teste estava verificando getId() enquanto o construtor tratava aquele parâmetro como protocolo. A suíte é melhor que testar tudo manualmente com curl porque executa os mesmos cenários de forma rápida e repetível e compara automaticamente o resultado esperado com o comportamento real. Além disso, os testes conseguem verificar exceções e chamadas ao repository com Mockito, algo mais trabalhoso de validar apenas pela API.
### 2. Mock e injeção de dependência (Aulas 13 a 15)
No AgendaServiceTest, o @Mock cria um AtendimentoRepository falso e o
@InjectMocks o injeta no service. Explique a relação disso com o @Autowired
que o Spring faz em produção — quem "injeta" em cada mundo, e por que o teste
consegue rodar sem banco e sem subir o Spring?
Resposta: No teste do AgendaService, o @Mock cria um objeto falso de AtendimentoRepository, sem acesso a um banco de dados real. O @InjectMocks coloca esse mock dentro da instância do service usada pelo teste. Em produção, quem resolve as dependências é o Spring, por meio da injeção configurada com @Autowired ou pelo construtor. No AgendaService, a dependência foi organizada por injeção via construtor, deixando o AtendimentoRepository explícito e facilitando a criação da classe no teste. Assim, o JUnit consegue executar a lógica do service sem subir o contexto completo do Spring. O Mockito controla as respostas do repository e permite verificar, por exemplo, quando save() deve ou não ser chamado.
### 3. == vs .equals() (Aula 7)
Um dos bugs fazia o agendamento duplicado passar pela verificação de conflito.
Explique por que == entre Strings e LocalDateTime falhou aqui, por que ele
"funciona por sorte" com literais como "Rex", e o que a sua correção mudou.
Resposta: O bug de conflito de horário acontecia porque == compara referências de objetos, e não o conteúdo de String e LocalDateTime. Por isso, dois objetos poderiam representar o mesmo pet e o mesmo horário e ainda assim serem referências diferentes. Com literais como "Rex", o resultado pode parecer funcionar por causa do pool de Strings do Java, mas isso não deve ser usado como regra. Para comparar o conteúdo, a correção passou a usar equals(). No AgendaService, isso permite verificar corretamente se o nome do pet e a data/hora são iguais ao atendimento já agendado. Com a correção, o sistema identifica o conflito e lança HorarioOcupadoException em vez de permitir o agendamento ou gerar uma NullPointerException.
### 4. Sobrescrita vs sobrecarga (Aula 7)
Na classe Atendimento, o método getDuracaoMinutos() não recebe parâmetros e retorna 30 como padrão. A Tosa deveria sobrescrevê-lo para retornar 60, mas foi declarada como getDuracaoMinutos(String porte). Como a lista de parâmetros era diferente, o Java entendeu como uma sobrecarga (overload): um método novo, com outra assinatura, que convivia com o herdado. Já a sobrescrita (override) exige a mesma assinatura do método da classe pai, e é ela que ativa o polimorfismo. Por isso, quando o código chamava tosa.getDuracaoMinutos(), o método executado era o da classe pai, e a duração saía 30. O código compilava normalmente porque sobrecarga é válida em Java, e os testes originais não verificavam a duração. Só o teste novo deveDurar60MinutosQuandoForTosa revelou o problema. Se o método tivesse @Override, o compilador daria erro na hora, porque não existe nenhum getDuracaoMinutos(String) na classe pai para ser sobrescrito. A classe Banho já usava @Override e funcionava corretamente, o que mostra a diferença entre as duas.
Resposta: A diferença principal é a assinatura do método. Na sobrecarga, o nome pode ser igual, mas os parâmetros são diferentes; na sobrescrita, a subclasse precisa usar a mesma assinatura do método da superclasse. Nesse caso, Tosa criou getDuracaoMinutos(String porte) em vez de sobrescrever getDuracaoMinutos(). Por isso, uma chamada sem parâmetro continuava usando o método de Atendimento, retornando 30. O teste novo tornou essa regra visível e revelou o problema. A correção com a mesma assinatura e @Override faz o polimorfismo funcionar como esperado.
### 5. Singleton manual vs bean do Spring (Aula 14)
O GeradorProtocolo é um Singleton escrito à mão e causou um dos bugs.
Explique o que ele garante, qual foi o bug, e por que o AgendaService
(@Service) não corre o mesmo risco no container do Spring?
Resposta: O GeradorProtocolo é um Singleton implementado manualmente e deve devolver sempre a mesma instância por meio de getInstancia(). O bug acontecia porque a lógica que controlava essa instância única estava incorreta, permitindo a criação de mais de um objeto. A correção fez getInstancia() reutilizar a instância existente, preservando o comportamento esperado do Singleton. Já o AgendaService é anotado com @Service e é gerenciado pelo container do Spring. Nesse caso, o próprio Spring controla o ciclo de vida do bean e resolve suas dependências, em vez de a classe implementar manualmente uma lógica de Singleton. Assim, os mecanismos são diferentes: o GeradorProtocolo controla a própria instância, enquanto o Spring controla os beans registrados no contexto da aplicação.
### 6. Cobertura de testes: onde parar? (Aula 15)
Dos 6 testes novos que você escreveu, alguns ficaram vermelhos (revelaram
bugs) e outros verdes de cara (regras já corretas). Vale a pena manter os que
ficaram verdes? Em um projeto real com prazo, o que você priorizaria testar:
caminho feliz, caminhos de erro, ou 100% de cobertura? Justifique.
Resposta: Sim, vale a pena manter os testes que ficaram verdes, porque eles registram regras que já estavam corretas e evitam regressões futuras. Um exemplo é o teste05, que protege o cancelamento de um atendimento com status AGENDADO. Os testes que ficaram vermelhos também são importantes porque revelaram comportamentos que precisavam de correção, como preço e duração. Em um projeto real com prazo, eu priorizaria primeiro as regras de negócio e os caminhos de erro, porque são cenários que podem causar comportamentos incorretos e exceções. Depois, cobriria os caminhos felizes mais importantes. Buscar 100% de cobertura pode ser útil em alguns contextos, mas cobertura percentual sozinha não garante que os testes verificam as regras certas. O mais importante é ter testes que protejam os comportamentos críticos e os casos de falha.
---
## Parte 5 — Espaço livre (opcional)
Alguma dificuldade, dúvida ou comentário sobre o checkpoint?