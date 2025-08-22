# Variáveis de Ambiente
Documentação com todas as variáveis de ambiente usadas pelo backend.

## Variáveis Gerais
Variáveis com escopo mais geral.

* `SPRING_PROFILES_ACTIVE` — Variável que define o ambiente do Spring. Por padrão: 'dev'.
* `SERVER_PORT` — Porta em que o servidor Spring Boot executa. Por padrão: 8080.


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


## Variáveis de Mailing
Todas variáveis relacionadas a envio de e-mails etc.

* `MAIL_HOST` — Host SMTP usado para o envio de e-mails. Por exemplo: 'smtp.gmail.com'.
* `MAIL_PORT` — Porta definida para uso do serviço SMTP.
* `MAIL_USER` — Endereço de e-mail que o sistema usará para enviar outros e-mails.
* `MAIL_PASSWORD` — Senha/Senha de aplicativo usada para autenticação do *MAIL_USER*.
