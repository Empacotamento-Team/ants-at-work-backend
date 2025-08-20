# Modelos de e-mail
Documentação com todos os *templates* de email e suas variáveis.

## Primeiro Acesso
E-mail referente ao primeiro acesso de um usuário, no qual é informado que será necessário criar uma nova
senha após o uso da que foi enviada.

Arquivo: `first-access.hbs`

#### Variáveis
| Nome      | Descrição                                            |
|:----------|:-----------------------------------------------------|
| `usuario` | Primeiro nome do usuário que está recebendo a senha. |
| `link`    | Link que leva à página de login do frontend.         |
| `senha`   | A senha de primeiro acesso.                          |


## Redefinição de Senha
E-mail referente à redefinição de senha do usuário.

Arquivo: `password-reset.hbs`

#### Variáveis
| Nome      | Descrição                                                      |
|:----------|:---------------------------------------------------------------|
| `usuario` | Primeiro nome do usuário que está redefinindo a senha.         |
| `link`    | Link que leva ao frontend com o token de redefinição de senha. |
