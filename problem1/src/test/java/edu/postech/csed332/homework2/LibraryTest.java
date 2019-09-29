package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class LibraryTest {

    @Test
    public void testfindBooksNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooks("any"));
    }

    @Test
    public void testSaveLibraryToFile(){
        Library library = new Library();
        Collection collection1 = new Collection("collection 1");
        Collection collection2 = new Collection("collection 2");
        Collection subcollection1 = new Collection("subcollection 1");
        Collection subcollection2 = new Collection("subcollection 2");
        collection1.addElement(subcollection1);
        collection1.addElement(subcollection2);
        Book book1 = new Book("collection1book", Arrays.asList("Name 1", "Name 2"));
        Book book2 = new Book("subcollection1book", Arrays.asList("Name 1", "Name 3"));
        collection1.addElement(book1);
        subcollection1.addElement(book2);
        library.getCollections().add(collection1);
        library.getCollections().add(collection2);

        library.saveLibraryToFile("testLibrary.json");
    }

    @Test
    public void testCreateLibrary(){
        Library newLibrary = new Library("testLibrary.json");
        ArrayList<Collection> collections = (ArrayList)newLibrary.getCollections();
        Assertions.assertEquals(collections.size(), 2);
        Assertions.assertEquals(collections.get(0).getElements().size(), 3);
        Assertions.assertEquals(collections.get(0).getElements().get(2) instanceof Book, true);
    }

    @Test
    public void testFindBooks(){
        Library newLibrary = new Library("testLibrary.json");
        HashSet<Book> books = (HashSet)newLibrary.findBooks("collection 1");
        Assertions.assertEquals(books.size(), 2);
    }

    @Test
    public void testFindBooksByAuthor(){
        Library newLibrary = new Library("testLibrary.json");
        HashSet<Book> books = (HashSet)newLibrary.findBooksByAuthor("Name 1");
        Assertions.assertEquals(books.size(), 2);
        books = (HashSet)newLibrary.findBooksByAuthor("Name 3");
        Assertions.assertEquals(books.size(), 1);
    }

    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Library.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
