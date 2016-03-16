package com.university.mongodb.javadev.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by Vadim Kulinsky on 16.03.16.
 */
public class FreeMarkerRenderer {

    private Configuration configuration;

    public FreeMarkerRenderer() {
        this.configuration = new Configuration();
        configuration.setClassForTemplateLoading(FreeMarkerRenderer.class, "/");
    }

    public String render(String templatePath, Map<String, ?> params) throws Exception {
        StringWriter stringWriter = new StringWriter();
        Template template = configuration.getTemplate(templatePath);
        template.process(params, stringWriter);
        return stringWriter.toString();
    }
}
