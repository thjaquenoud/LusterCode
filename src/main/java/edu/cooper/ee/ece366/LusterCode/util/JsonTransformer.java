package edu.cooper.ee.ece366.LusterCode.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    private final Gson gson =
            new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

    @Override
    public String render(final Object model) throws Exception {
        return gson.toJson(model);
    }
}
