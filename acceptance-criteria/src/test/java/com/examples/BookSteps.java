package com.examples;


import com.examples.book.Book;
import com.examples.book.Library;
import cucumber.api.Format;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by alee2 on 8/2/17.
 *
 * @author alee2
 */
public class BookSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @Given(".+book with the title '(.+)', written by '(.+)', published in (.+)")
    public void addNewBook(final String title, final String author, @Format("dd MMMMM yyyy") final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("^the customer searches for books published between (\\d+) and (\\d+)$")
    public void setSearchParameters(@Format("yyyy") final Date from, @Format("yyyy") final Date to) {
        result = library.findBooks(from, to);
    }

    @Then("(\\d+) books should have been found$")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("Book (\\d+) should have the title '(.+)'$")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
}
