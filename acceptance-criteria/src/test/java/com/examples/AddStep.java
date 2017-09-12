package com.examples;

import com.examples.calculator.Add;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by alee2 on 8/2/17.
 *
 * @author alee2
 */
public class AddStep {
    Add add;

    @Given("^a number (\\d+) with another number (\\d+)$")
    public void a_number_with_another_number(int arg1, int arg2) throws Throwable {
        Add add = new Add(arg1, arg2);
        this.add = add;
    }

    @When("^the calculator wants to add them together$")
    public void when_the_calculator_wants_to_add_them_together() throws Throwable {
        add.add();
    }

    @Then("^the result is (\\d+)$")
    public void the_result_is(int arg1) throws Throwable {
        assertThat(add.add(), equalTo(arg1));
    }
}
