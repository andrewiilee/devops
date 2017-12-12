package com.example;

import com.example.enumeration.OrderState;
import com.example.json.SFOrder;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SFMain.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendFileStep {
    @Autowired
    private TestRestTemplate template;

    @Given("^the server is running$")
    public void the_server_is_running() throws Throwable {
    }

    @When("^the server receives a reset request with order id$")
    public void the_server_receives_a_reset_request_with_order_id(DataTable arg1) throws Throwable {
        SFOrder order = template.getForObject("/reset/order3", SFOrder.class);
        assertThat(order.getId(), equalTo(arg1.raw().get(0).get(0)));
        assertThat(order.getState(), equalTo(OrderState.READY));
    }

    @Then("^the order state and associated file state are set to READY$")
    public void the_order_state_and_associated_file_state_are_set_to_READY(DataTable arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
    }

    @When("^the server receives a reset all request$")
    public void the_server_receives_a_reset_all_request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^all the order state and file state is set to READY$")
    public void all_the_order_state_and_file_state_is_set_to_READY() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Given("^there are a list of orders with READY state with files in a READY state$")
    public void there_are_a_list_of_orders_with_READY_state_with_files_in_a_READY_state(DataTable arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
    }

    @When("^a request to update a file to DONE$")
    public void a_request_to_update_a_file_to_DONE() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^the result should update the file state to DONE$")
    public void the_result_should_update_the_file_state_to_DONE(DataTable arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
    }

    @Then("^any state changes logged at info level$")
    public void any_state_changes_logged_at_info_level() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^a request for all READY orders with start (\\d+) and returns (\\d+) items$")
    public void a_request_for_all_READY_orders_with_start_and_returns_items(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^the result should return item (\\d+) to (\\d+)$")
    public void the_result_should_return_item_to(int arg1, int arg2, DataTable arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
    }

}
