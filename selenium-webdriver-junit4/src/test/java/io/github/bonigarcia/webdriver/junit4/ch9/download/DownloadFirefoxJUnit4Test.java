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
package io.github.bonigarcia.webdriver.junit4.ch9.download;

import java.io.File;
import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DownloadFirefoxJUnit4Test {

    WebDriver driver;

    @Before
    public void setup() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.dir",
                new File(".").getAbsolutePath());
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk",
                "image/png, application/pdf");
        options.addPreference("pdfjs.disabled", true);

        driver = WebDriverManager.firefoxdriver().capabilities(options)
                .create();
    }

    @After
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    public void testDownloadFirefox() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/download-files.html");

        driver.findElement(By.xpath("(//a)[2]")).click();
        driver.findElement(By.xpath("(//a)[3]")).click();
    }

}
