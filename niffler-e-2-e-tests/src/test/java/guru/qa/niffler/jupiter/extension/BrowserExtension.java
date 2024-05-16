package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;
import static org.openqa.selenium.OutputType.BYTES;

public class BrowserExtension implements TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler, AfterEachCallback {

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        doScreenShot();
        throw throwable;
    }

    //нельзя работать с браузером в beforeAll, так как методы beforeAll выполняются в отдельном потоке, он не привязан
    //к потоку выполнения теста; и внутри селенида WebDriverManager - ThreadLocal, он привязан к потоку.
    //в многопоточке такие тесты работать не будут

    @Override
    public void handleBeforeEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenShot();
        throw throwable;
    }

    @Override
    public void handleAfterEachMethodExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        doScreenShot();
        throw throwable;
    }

    private void doScreenShot() {
        if (hasWebDriverStarted()) {
            Allure.addAttachment(
                    "Screen on test end",
                    new ByteArrayInputStream(
                            Objects.requireNonNull(
                                    screenshot(BYTES)
                            )
                    )
            );
        }
    }

    //Селенид из коробки переиспользует браузер между тестами. Он чистит его перед новым использованием

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (hasWebDriverStarted()) {
            closeWebDriver();
        }
    }

}