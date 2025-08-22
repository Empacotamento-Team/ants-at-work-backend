# Entidades
Todas as entidades usadas no contexto de identidade.

## User
A entidade de usuário. Ela é de suma importância para o funcionamento completo do sistema. Abaixo
se destaca todas as propriedades e métodos presentes no contexto de identidade.

### Propriedades
| Nome           | Tipo            | Descrição                                                                                            |
|:---------------|:----------------|:-----------------------------------------------------------------------------------------------------|
| `id`           | `Long`          | O id do usuário. Geralmente importante quando se trata de um usuário já existente no banco de dados. |
| `name`         | `String`        | O nome do usuário.                                                                                   |
| `email`        | `Email`         | O e-mail do usuário.                                                                                 |
| `passwordHash` | `String`        | O hash da senha do usuário, usado para comparação no login.                                          |
| `roles`        | `Set<UserRole>` | Os cargos que o usuário possui, exemplo: 'Admin' etc.                                                |

### Métodos
| Método                                      | Retorno | Descrição                                                          |
|:--------------------------------------------|:--------|:-------------------------------------------------------------------|
| `changeName(String newName)`                | `void`  | Altera o nome caso este seja válido e possua mais de 3 caracteres. |
| `changeEmail(Email newEmail)`               | `void`  | Altera o e-mail caso este seja válido (não nulo).                  |
| `changePasswordHash(String newPasswordHash` | `void`  | Altera o hash da senha caso este seja válido.                      |

---

## RefreshToken
A entidade de refresh token. Ela é importante para o sistema de autenticação e a segurança. Abaixo segue
suas propriedades e métodos.

### Propriedades
| Nome         | Tipo      | Descrição                                                                         |
|:-------------|:----------|:----------------------------------------------------------------------------------|
| `id`         | `Long`    | O id do refreshToken.                                                             |
| `userId`     | `Long`    | O id do usuário vinculado ao token.                                               |
| `tokenHash`  | `String`  | O hash do token, garantindo a segurança.                                          |
| `expiration` | `Instant` | O momento em que o token expira, no banco sendo salvo como 'AAAA-MM-DD HH:MM:ss'. |

---

## PasswordResetToken
A entidade que representa o token de redefinição de senha. Ela é importante a partir do momento que ela armazena o
hash do token e a data de expiração, proporcionando segurança e funcionalidade.

### Propriedades
| Nome         | Tipo      | Descrição                                                                         |
|:-------------|:----------|:----------------------------------------------------------------------------------|
| `id`         | `Long`    | O id do token de redefinição de senha.                                            |
| `userId`     | `Long`    | O id do usuário vinculado ao token.                                               |
| `tokenHash`  | `String`  | O hash do token, garantindo a segurança.                                          |
| `expiration` | `Instant` | O momento em que o token expira, no banco sendo salvo como 'AAAA-MM-DD HH:MM:ss'. |
