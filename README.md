# Olimpíada de Questões — Refatoração

## Sobre o projeto

Este repositório contém a refatoração do sistema de olimpíada de questões de xadrez. O código original foi reestruturado aplicando os **cinco princípios SOLID**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation e Dependency Inversion.

A lógica de negócio, as funcionalidades e as dependências externas foram mantidas apenas a organização interna do código foi alterada.

---

## Principais mudanças

O código foi dividido em **5 pacotes**, cada um com uma responsabilidade clara:

```
br.com.ucsal.olimpiadas
├── model/       → dados + hierarquia de Questao + factories
├── repository/  → interface genérica + implementações em memória
├── service/     → lógica de negócio + estratégias de cálculo de nota
├── ui/          → menu + renderizadores de tabuleiro
└── seed/        → carga inicial de dados
```

O `App.java` virou Composition Root: é o único lugar que instancia e conecta dependências. Para trocar comportamento (nota, tabuleiro, repositório), basta mudar uma linha no `App.java`.

---

## Aplicação dos princípios SOLID

### S — Single Responsibility Principle

> Cada classe tem uma única razão para mudar.

| Classe                         | Responsabilidade                    |
| ------------------------------ | ----------------------------------- |
| `ConsoleUI`                    | Controlar menu e comandos           |
| `CadastrarParticipanteCommand` | Cadastrar participante              |
| `CadastrarProvaCommand`        | Cadastrar prova                     |
| `CadastrarQuestaoCommand`      | Cadastrar questão                   |
| `AplicarProvaCommand`          | Conduzir prova                      |
| `ListarTentativasCommand`      | Exibir tentativas                   |
| `AsciiBoardRenderer`           | Renderizar tabuleiro ASCII          |
| `UnicodeBoardRenderer`         | Renderizar tabuleiro Unicode        |
| `AcertosNotaCalculator`        | Calcular nota só com acertos        |
| `PenalizacaoNotaCalculator`    | Calcular nota com penalidade        |
| `BaseInMemoryRepository`       | Armazenar entidades + gerenciar IDs |
| `DataSeeder`                   | Popular dados iniciais              |
| `QuestaoSimplesFactory`        | Criar QuestaoSimples                |
| `QuestaoComFenFactory`         | Criar QuestaoComFen                 |


---

### O — Open/Closed Principle

> Classes devem estar abertas para extensão, mas fechadas para modificação.

Exemplos:

**1. Estratégia de cálculo de nota**

```java
NotaCalculator notaCalculator = new AcertosNotaCalculator();// só conta acertos
// ou
NotaCalculator notaCalculator = new PenalizacaoNotaCalculator(0.25);// desconta pontos por erro
```
Para criar uma nova regra, basta implementar NotaCalculator.
Nenhuma outra classe precisa ser alterada.

**2. Renderização do tabuleiro**

```java
BoardRenderer boardRenderer = new AsciiBoardRenderer();    // letras e pontos
BoardRenderer boardRenderer = new UnicodeBoardRenderer();  // símbolos de xadrez (♔ ♛ ♟)
// Para adicionar outro estilo, basta criar outra implementação de BoardRenderer.
```

**3. Tipo de questão**

```java
var questaoFactories = List.of(
    new QuestaoComFenFactory(),   // cria QuestaoComFen se fenInicial não for vazio
    new QuestaoSimplesFactory()   // fallback: cria QuestaoSimples
);
// Para adicionar um novo tipo de questão (ex: QuestaoComImagem), criar uma nova factory e registrar aqui.
```
**4. Comandos do menu**

```java
// Para adicionar uma nova opção no menu, só inclui na lista no App.java:
var comandos = List.of(
    new CadastrarParticipanteCommand(...),
    new CadastrarProvaCommand(...),
    // novo comando aqui — ConsoleUI não precisa de nenhuma alteração
);
```
---

### L — Liskov Substitution Principle

Subtipos substituem a classe base sem quebrar nada:

Questao → base
QuestaoSimples → questão textual
QuestaoComFen → questão com tabuleiro FEN

Todos os serviços (QuestaoService, TentativaService, AplicarProvaCommand) funcionam igual com qualquer subtipo.

BaseInMemoryRepository<T> também respeita LSP: qualquer repositório concreto pode substituir a interface Repository<T>.

---

### I — Interface Segregation Principle

Interfaces pequenas, específicas e sem métodos desnecessários:

Repository<T> → salvar, listarTodos, buscarPorId, existePorId
MenuCommand → tecla(), rotulo(), executar()
BoardRenderer → renderizar(String fen)
NotaCalculator → calcular(Tentativa)
QuestaoFactory → aceita(String fenInicial), criar(String fenInicial)

### D — Dependency Inversion Principle

> Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações.

Todas as dependências são fornecidas via construtor como abstrações, nunca como classes concretas.

```java
// TentativaService depende de abstrações, não de implementações
public TentativaService(Repository<Tentativa> repository,
                        NotaCalculator notaCalculator) { ... }

// AplicarProvaCommand depende da abstração BoardRenderer
public AplicarProvaCommand(..., BoardRenderer boardRenderer, ...) { ... }

// QuestaoService depende da lista de abstrações QuestaoFactory
public QuestaoService(InMemoryQuestaoRepository repository,
                      List<QuestaoFactory> factories) { ... }
```

`RepositoryFactory` centraliza a criação dos repositórios concretos. `App.java` é o único Composition Root — o único lugar onde implementações concretas são instanciadas e fornecidas.

---

## Restrições respeitadas

- Não alterar a lógica de negócio original
- Não remover funcionalidades existentes
- Não adicionar frameworks externos (ex: Spring)
- pom.xml só com JUnit 5 para testes

---

## Como executar

```bash
# Compilar
./mvnw compile

# Executar
./mvnw exec:java -Dexec.mainClass="br.com.ucsal.olimpiadas.App"

# Testes
./mvnw test
```

---

## Estrutura de arquivos

```
src/main/java/br/com/ucsal/olimpiadas/
├── App.java
├── model/
│   ├── Participante.java
│   ├── Prova.java
│   ├── Questao.java
│   ├── QuestaoSimples.java
│   ├── QuestaoComFen.java
│   ├── QuestaoFactory.java
│   ├── QuestaoSimplesFactory.java
│   ├── QuestaoComFenFactory.java
│   ├── Resposta.java
│   └── Tentativa.java
├── repository/
│   ├── Repository.java
│   ├── BaseInMemoryRepository.java
│   ├── InMemoryRepositories.java
│   ├── InMemoryQuestaoRepository.java
│   └── RepositoryFactory.java
├── service/
│   ├── NotaCalculator.java
│   ├── AcertosNotaCalculator.java
│   ├── PenalizacaoNotaCalculator.java
│   ├── ParticipanteService.java
│   ├── ProvaService.java
│   ├── QuestaoService.java
│   └── TentativaService.java
├── ui/
│   ├── MenuCommand.java
│   ├── ConsoleUI.java
│   ├── BoardRenderer.java
│   ├── AsciiBoardRenderer.java
│   ├── UnicodeBoardRenderer.java
│   ├── CadastrarParticipanteCommand.java
│   ├── CadastrarProvaCommand.java
│   ├── CadastrarQuestaoCommand.java
│   ├── AplicarProvaCommand.java
│   └── ListarTentativasCommand.java
└── seed/
    └── DataSeeder.java
```
