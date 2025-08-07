# Variáveis de Ambiente
Documentação com todas as variáveis de ambiente usadas pelo backend.

## Variáveis Gerais
Variáveis com escopo mais geral.

* `SPRING_PROFILES_ACTIVE` Variável que define o ambiente do Spring. Por padrão: 'dev'.


## Variáveis de Segurança
Todas as variáveis relacionadas à segurança.

* `API_KEY` — Valor usado para acesso aos endpoints via API Key.
* `JWT_SECRET` — Segredo usado para produzir os tokens JWT.


## Variáveis de Database
Todas as variáveis relacionadas à base de dados.

* `DB_HOST` — Host onde se encontra o banco de dados. Por padrão: 'localhost'.
* `DB_NAME` — Nome da database criada no banco de dados. Por padrão: 'antsatwork'.
* `DB_PASSWORD` — Senha do usuário para acesso ao banco de dados. Por padrão: '1234'.
* `DB_PORT` — Porta de acesso ao banco de dados. Por padrão: 3306.
* `DB_USERNAME` — Nome de usuário para acesso ao banco de dados. Por padrão: 'root'.
