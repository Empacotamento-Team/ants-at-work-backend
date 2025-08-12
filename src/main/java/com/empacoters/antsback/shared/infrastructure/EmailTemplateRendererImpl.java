package com.empacoters.antsback.shared.infrastructure;

import com.empacoters.antsback.shared.application.services.EmailTemplateRenderer;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailTemplateRendererImpl implements EmailTemplateRenderer {
    @Override
    public String render(String templateName, Map<String, Object> data) {
        Handlebars handlebars = new Handlebars()
            .with(new FileTemplateLoader("src/main/resources/email-templates", ".hbs"));

        try {
            var template = handlebars.compile(templateName);
            return template.apply(data);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
