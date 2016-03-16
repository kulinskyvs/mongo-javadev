package com.university.mongodb.javadev.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by Vadim Kulinsky on 16.03.16.
 */
public class FreeMarkerRenderer {

    private Configuration configuration;

    public FreeMarkerRenderer() {
        this.configuration = new Configuration(new Version("2.3.23"));
        configuration.setClassForTemplateLoading(FreeMarkerRenderer.class, "/");
    }

    public String render(String templatePath, Map<String, ?> params) throws Exception {
        StringWriter stringWriter = new StringWriter();
        Template template = configuration.getTemplate(templatePath);
        template.process(params, stringWriter);
        return stringWriter.toString();
    }
}
