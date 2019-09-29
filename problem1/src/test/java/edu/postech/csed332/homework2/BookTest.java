package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.*;

import java.util.ArrayList;
import java.util.Arrays;

public class BookTest {

    @Test
    public void testGetTitle() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(book.getTitle(), "Unit Testing");
    }

    @Test
    public void testGetAuthors(){
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));

        Assertions.assertEquals(book.getAuthors().get(0), "Name 1");
        Assertions.assertEquals(book.getAuthors().get(1), "Name 2");
    }

    @Test
    public void testGetStringRepresentation(){
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        JSONObject jsonBook = new JSONObject();
        jsonBook.put("title", "Unit Testing");
        JSONArray jsonAuthors = new JSONArray();
        jsonAuthors.put("Name 1");
        jsonAuthors.put("Name 2");
        jsonBook.put("authors", jsonAuthors);
        Assertions.assertEquals(book.getStringRepresentation(), jsonBook.toString());
    }

    @Test
    public void testBookFromStrRepr(){
        JSONObject jsonBook = new JSONObject();
        jsonBook.put("title", "Unit Testing");
        JSONArray jsonAuthors = new JSONArray();
        jsonAuthors.put("Name 1");
        jsonAuthors.put("Name 2");
        jsonBook.put("authors", jsonAuthors);

        Book book = new Book(jsonBook.toString());

        Assertions.assertEquals(book.getTitle(), "Unit Testing");
        Assertions.assertEquals(book.getAuthors().get(0), "Name 1");
        Assertions.assertEquals(book.getAuthors().get(1), "Name 2");
    }

    @Test
    public void getContainingCollections(){
        Collection collection1 = new Collection("collection 1");
        Collection collection2 = new Collection("collection 2");
        Collection collection3 = new Collection("collection 3");
        collection2.addElement(collection3);
        collection1.addElement(collection2);
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        collection3.addElement(book);

        Assertions.assertEquals(book.getContainingCollections().size(), 3);
        Assertions.assertEquals(book.getContainingCollections().get(0), collection3);
        Assertions.assertEquals(book.getContainingCollections().get(1), collection2);
        Assertions.assertEquals(book.getContainingCollections().get(2), collection1);
    }
    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Book.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
