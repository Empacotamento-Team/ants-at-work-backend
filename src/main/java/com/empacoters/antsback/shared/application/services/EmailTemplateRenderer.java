package com.empacoters.antsback.shared.application.services;

import java.util.Map;

public interface EmailTemplateRenderer {
    String render(String templateName, Map<String, Object> data);
}
