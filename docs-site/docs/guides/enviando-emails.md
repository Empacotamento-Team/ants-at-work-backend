---
sidebar_position: 3
---

# Enviando e-mails
Se um dos comportamentos que você deseja na feature que está desenvolvendo for o envio de e-mails, 
nós temos uma solução já feita: o `EmailSender`.

## Usando o `EmailSender`
Este é um serviço que pode ser incorporado na sua classe por meio do `@Autowired` ou, preferencialmente,
por meio da maneira tradicional no construtor:

```java title="Exemplo de serviço"
import com.empacoters.antsback.shared.application.services.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    private final EmailSender emailSender;

    public ExampleService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    /* ... */
}
```

### Enviando e-mails simples
Com este serviço você pode usá-lo para enviar mensagens mais simples como um "Olá Mundo!".

```java title="Mensagem simples"
@Service
public class ExampleService {
    private final EmailSender emailSender;

    /* Conteúdo desnecessário omitido... */

    public void enviarOla() {
        var destinatarios = new String[] {"noreply@exemplo1", "noreply@exemplo2"};
        var mensagem = "Olá Mundo!";
        var assunto = "Bem-vindo!";

        // Envia um e-mail para os destinatários com o assunto "Bem-vindo!" e a devida mensagem.
        this.emailSender.send(destinatarios, assunto, mensagem);
    }
}
```

### Enviando e-mails com HTML
Além de ser possível enviar mensagens simples, o `EmailSender` trata a mensagem como um HTML. Então é possível
enviar mensagens com trechos em negrito, itálico, imagens etc. Veja um breve exemplo:

```java title="Mensagem com HTML"
@Service
public class ExampleService {
    private final EmailSender emailSender;

    /* Conteúdo desnecessário omitido... */

    public void enviarOlaComHtml() {
        var destinatarios = new String[] {"noreply@exemplo1", "noreply@exemplo2"};
        var mensagem = "<p>Olá <strong>Mundo!</strong></p>";  // "Mundo!" fica em negrito no corpo do e-mail.
        var assunto = "Bem-vindo!";

        this.emailSender.send(destinatarios, assunto, mensagem);
    }
}
```


## Usando o `EmailTemplateRenderer`
Para facilitar o uso do HTML nos e-mails e prover mais praticidade, o uso de *templates* é possível!
É possível encontrar e adicionar modelos na pasta `resources/email-templates`. Todos são do formato `.hbs` (Handlebars),
um formato popular no contexto de desenvolvimento Javascript/Typescript.

Arquivos Handlebars funcionam a partir da sintaxe [Mustache](https://mustache.github.io/). Por isso, são uma boa forma de
construir modelos de texto e possuem funcionalidades que facilitam no desenvolvimento. Para mais detalhes sobre e
como usar, acesse [este link](https://jknack.github.io/handlebars.java/gettingStarted.html). Ou se preferir, acesse
a documentação do [Handlebars.js](https://handlebarsjs.com/), que serviu de inspiração para esta biblioteca.

### Demonstração
Da mesma forma que foi que o `EmailSender` foi incorporado à classe, o `EmailTemplateRenderer` pode ser feito da mesma
forma. Com o uso deste serviço, é possível carregar facilmente um modelo de e-mail com os dados necessários. Veja como:

```handlebars title="Exemplo de arquivo Handlebars: 'ola-mundo.hbs'"
<p>Olá {{ola-a-quem}}!</p>
```

```java title="Uso do EmailTemplateRenderer"
@Service
public class ExampleService {
    private final EmailSender emailSender;
    private final EmailTemplateRenderer emailTemplateRenderer;

    /* Conteúdo desnecessário omitido... */

    public void enviarOlaComTemplate() {
        var destinatarios = new String[] {"noreply@exemplo1", "noreply@exemplo2"};
        var assunto = "Bem-vindo!";

        // Carregamento do modelo
        var modelo = "ola-mundo";  // Nome do arquivo sem extensão. Por exemplo: ex1.hbs -> ex1

        Map<String, Object> dados = new HashMap<>();  // Dados que serão injetados no modelo.
        dados.put("ola-a-quem", "Mundo");  // Definindo valor da chave "ola-a-quem" como "Mundo".

        var mensagem = this.emailTemplateRenderer.render(modelo, dados);  // Carrega o modelo e recebe uma String.

        // Envio da mensagem
        this.emailSender.send(destinatarios, assunto, mensagem);  // <p>Olá Mundo!</p>
    }
}
```
