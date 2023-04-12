package com.example.source;
public class ServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SourceApplication.class);
    }

}
