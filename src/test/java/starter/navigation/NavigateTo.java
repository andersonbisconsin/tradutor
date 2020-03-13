package starter.navigation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.thucydides.core.annotations.Step;

public class NavigateTo {

    DuckDuckGoHomePage duckDuckGoHomePage;

    @Step("Open the DuckDuckGo home page")
    public void theDuckDuckGoHomePage() {
        duckDuckGoHomePage.open();
    }
}
