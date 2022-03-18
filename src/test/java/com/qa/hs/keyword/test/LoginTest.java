package com.qa.hs.keyword.test;

import com.qa.hs.keyword.engine.KeyWordEngine;
import org.testng.annotations.Test;

public class LoginTest {

    public KeyWordEngine keyWordEngine;
    @Test

    public void loinTest() throws InterruptedException {
        keyWordEngine= new KeyWordEngine();
        keyWordEngine.startExecution("login");
        Thread.sleep(2000);
    }
}
