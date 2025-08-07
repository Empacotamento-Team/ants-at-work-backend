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

## Usando as variáveis de ambiente
Para garantirmos a segurança ao fazer contribuições e flexibilidade no ambiente de desenvolvimento, vamos usar as
variáveis de ambiente no arquivo `.env`. Dessa forma, é possível concentrar todas as variáveis importantes em um só 
lugar e mesmo assim ter segurança, pois este arquivo está contido no `.gitignore`.

Em resumo, no repositório está contido o arquivo `.env.sample`, duplique o arquivo e renomeie o duplicado para `.env`.
Depois disso, altere conforme sua necessidade as variáveis como `DB_NAME`, `DB_PASSWORD` etc. Por fim, é necessário
configurar a IDE para que ela carregue automaticamente as variáveis quando iniciar o projeto.

### Configurando o IntelliJ
Ao usar o IntelliJ como IDE, precisamos configurá-lo para dar suporte ao arquivo `.env`. A primeira coisa necessária a
se fazer é instalar o plugin que dá essa funcionalidade.

#### Instalando o Plugin
Estando no arquivo `.env`, você provavelmente irá notar um pequeno aviso sobre a instalação de um plugin, como aparece
na foto abaixo. Clique em **Install plugins**.
![1º Passo Instalando o Plugin](/img/primeiros-passos/intellij-env-plugin-1.png)

Após isso, aparecerá uma pequena janela como mostrada abaixo. Selecione o plugin selecionado na foto e clique em **Ok**.
<img alt="2º Passo Instalando o Plugin" height="450" src="/img/primeiros-passos/intellij-env-plugin-2.png"/>

#### Configurando a inicialização
Após instalar o plugin, é necessário passar ao IntelliJ o caminho do arquivo `.env` para que ele possa ler e aplicar as
variáveis na inicialização. Para isso precisamos entrar *Run/Debug Configurations* da IDE da forma que é mostrado
abaixo. Clique em **Edit configurations**.
![1º Passo Configurando Inicialização](/img/primeiros-passos/intellij-run-config-1.png)

Após o clique do botão, uma nova janela se abrirá. Primeiro confira se não há algum perfil de inicialização do tipo
Spring Boot. Caso não haja, clique no '+' no canto superior esquerdo e crie um novo, dê um nome, e no **Build and run**
ponha a classe principal, a *AntsBackApplication*.

O foco agora se concentra no campo **Environment variables**. Caso ele não esteja aparecendo, clique em *Modify options*
e habilite-o. Com ele habilitado, clique no ícone da pasta e dê o caminho para o arquivo `.env`. Após isso, utilize esse
perfil de inicialização e a aplicação estará pronta para ser executada!
![2º Passo Configurando Inicialização](/img/primeiros-passos/intellij-run-config-2.png)
