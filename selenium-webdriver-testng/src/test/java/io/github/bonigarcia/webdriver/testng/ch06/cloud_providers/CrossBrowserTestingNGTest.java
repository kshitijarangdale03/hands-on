/*
 * (C) Copyright 2021 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.webdriver.testng.ch06.cloud_providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CrossBrowserTestingNGTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        String username = System.getProperty("crossBrowserTestingUsername");
        String accessKey = System.getProperty("crossBrowserTestingAccessKey");

        // An alternative way to read username and key is using envs:
        // String username = System.getenv("CROSSBROWSERTESTING_USERNAME");
        // String accessKey = System.getenv("CROSSBROWSERTESTING_ACCESS_KEY");

        assumeThat(username).isNotEmpty();
        assumeThat(accessKey).isNotEmpty();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "84");
        capabilities.setCapability("platformName", "Windows 10");
        capabilities.setCapability("name", "My test name");

        Map<String, String> cbtOptions = new HashMap<>();
        cbtOptions.put("record_video", "true");
        cbtOptions.put("screenResolution", "1366x768");
        capabilities.setCapability("cbt:options", cbtOptions);

        URL remoteUrl = new URL(
                String.format("http://%s:%s@hub.crossbrowsertesting.com/wd/hub",
                        username, accessKey));
        driver = new RemoteWebDriver(remoteUrl, capabilities);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCrossBrowserTesting() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
