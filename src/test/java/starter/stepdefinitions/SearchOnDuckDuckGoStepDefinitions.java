package starter.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import starter.navigation.*;
import static org.assertj.core.api.Assertions.assertThat;
import static starter.matchers.TextMatcher.textOf;

public class SearchOnDuckDuckGoStepDefinitions {

    @Steps
    NavigateTo navigateTo;
    
    DuckDuckGoHomePage duck;

    @Given("^traduzir$")
    public void traduzir() {
        navigateTo.theDuckDuckGoHomePage();
        
        duck.traduzValor();
    }
}
