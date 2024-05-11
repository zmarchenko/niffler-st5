package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class CategoryExtension implements BeforeEachCallback {

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final SpendApi spendApi = retrofit.create(SpendApi.class);

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateCategory.class
        ).ifPresent(
                generateCategory -> {
                    CategoryJson categoryJson = new CategoryJson(
                            null,
                            generateCategory.category(),
                            generateCategory.username()
                    );

                    try {
                        spendApi.createCategory(categoryJson).execute();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

}
