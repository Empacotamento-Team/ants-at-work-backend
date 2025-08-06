# Configuração de Ambiente

Para ser possível começarmos a desenvolver, é necessário que configuremos o ambiente de desenvolvimento a
fim de o deixar pronto para a ação. 

## Banco de Dados
Como se sabe, o projeto utiliza do banco de dados MySQL para armazenar e lidar com os dados necessários.
Por isso, é muito importante que o banco esteja instalado e configurado na sua máquina.
***O projeto não funciona sem essa configuração!***

É necessário que seja feita no banco a criação de uma schema com nome **antsatwork**. O software depende dessa schema
para iniciar e funcionar. O nome por padrão é esse, porém pode ser mudado conforme a variável de ambiente `DB_NAME`.
Mais variáveis de ambiente, como senha do banco etc podem ser vistas na *seção de database* das [variáveis
de ambiente](../../reference/variaveis-de-ambiente#variáveis-de-database).
