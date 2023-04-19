package io.kshyk.common;

import com.codeborne.selenide.appium.AppiumDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class TestResultLoggerExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        var driver = AppiumDriverRunner.getMobileDriver();
        if (context.getExecutionException().isEmpty()) {
            log.info("Test Successful for test {}: ", context.getDisplayName());
            driver.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test finished successfully\"}}");
        } else {
            log.info("Test Failed for test {}: ", context.getDisplayName());
            driver.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"" + context.getExecutionException().toString() + "\"}}");
        }
    }
}
