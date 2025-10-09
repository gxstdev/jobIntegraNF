## jobIntegraNf

Sistema simples de processamento de Notas Fiscais (NFs) em jobs, com geração de arquivos `.txt`, envio por e-mail e expurgo.

### Visão Geral

- **Jobs**: processamento (`JobProcessaNFs`), envio por e-mail (`JobEnviaNFsEmail`) e expurgo (`JobExpurgo`).
- **Serviços**:
  - `NFService`: persistência e atualização de status das NFs.
  - `ArquivoNFService`: geração/validação/listagem de arquivos de NFs.
  - `EmailService`: envio de e-mails com anexos.
  - `ParametroSistemaService`: leitura de parâmetros (diretórios, lotes, SMTP, destinatário etc.).
- **DAO/JPA**: `GenericDAO`, `NFDAO`, `ParametroSistemaDAO` com `JPAUtil` para `EntityManager`.
- **E-mail**: `EmailSenderImpl` (Jakarta Mail/Angus) e `EmailComposerImpl`.
- **Util**: `FileUtil` (mover/expurgar/gerar batch) e `NFUtil` (dados de exemplo).

### Como executar

1. Configurar `persistence.xml` com seu banco (Oracle no exemplo) e tabelas `TB_NF` e `TB_PARAMETRO_SISTEMA`.
2. Popular `TB_PARAMETRO_SISTEMA` com os parâmetros em `Parametros` (diretórios, lotes, SMTP e e-mails).
3. Gerar o JAR com dependências:
```bash
mvn -q -e -DskipTests package
```
4. Executar o job principal (classe `org.jobIntegraNf.job.ExecutaJob`).

### Estrutura dos jobs

Todos os jobs seguem o template de `AbstractJobArquivos`:
1. Obter arquivos a processar.
2. Processar em batches (`FileUtil.gerarBatchArquivos`).
3. Em caso de sucesso, executar ação pós-processamento (mover/expurgar).

### Histórico de desenvolvimento

- Inicialmente implementado com conhecimentos pessoais de estudos em Java e experiência em projetos.
- Posteriormente, refatorado e documentado com ajuda de IA e da IDE Cursor (aplicação de JavaDoc abrangente, organização de camadas e melhorias de clareza).

### Tecnologias

- Java 17, Maven
- JPA/Hibernate 6
- Jakarta Mail (Angus)
- SLF4J + Logback

### Bibliotecas de Testes

- JUnit Jupiter (JUnit 5): `org.junit.jupiter:junit-jupiter-api:5.10.0`
- Mockito Core: `org.mockito:mockito-core:5.5.0`
- Mockito JUnit Jupiter: `org.mockito:mockito-junit-jupiter:5.5.0`

### Observações

- O `persistence-unit` esperado é o `JobIntegraNF`.
- O `maven-assembly-plugin` configura uma entrada principal para `ExecutaJob`.
- Garanta a criação e permissões de leitura/escrita nos diretórios configurados.


