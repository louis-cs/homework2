package edu.postech.csed332.homework2;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionTest {

    @Test
    public void testGetName() {
        Collection col = new Collection("Software");
        Assertions.assertEquals(col.getName(), "Software");
    }

    @Test
    public void testGetElements(){
        Collection collection1 = new Collection("collection 1");
        Collection subcollection1 = new Collection("subcollection 1");
        Book book = new Book("collection1book", Arrays.asList("Name 1", "Name 2"));
        collection1.addElement(subcollection1);
        collection1.addElement(book);
        ArrayList<Element> elements = (ArrayList)collection1.getElements();


        Assertions.assertEquals(elements.size(), 2);
        Assertions.assertEquals(((Collection)elements.get(0)).getName(), "subcollection 1");
        Assertions.assertEquals(((Book)elements.get(1)).getTitle(), "collection1book");
    }


    @Test
    public void testAddElement(){
        Collection collection1 = new Collection("collection 1");
        Assertions.assertEquals(collection1.getElements().size(), 0);

        Book book = new Book("collection1book", Arrays.asList("Name 1", "Name 2"));
        collection1.addElement(book);
        Assertions.assertEquals(collection1.getElements().size(), 1);
        Assertions.assertEquals(((Book)collection1.getElements().get(0)).getTitle(), "collection1book");

    }

    @Test
    public void testDeleteElement(){
        Collection collection1 = new Collection("collection 1");
        Book book = new Book("collection1book", Arrays.asList("Name 1", "Name 2"));
        collection1.addElement(book);
        Assertions.assertEquals(collection1.getElements().size(), 1);
        collection1.deleteElement(book);
        Assertions.assertEquals(collection1.getElements().size(), 0);
    }

    @Test
    public void testGetStringRepresentation(){
        Collection collection1 = new Collection("collection 1");
        Collection subcollection1 = new Collection("subcollection 1");
        Collection subcollection2 = new Collection("subcollection 2");
        collection1.addElement(subcollection1);
        collection1.addElement(subcollection2);
        Book book1 = new Book("collection1book", Arrays.asList("Name 1", "Name 2"));
        Book book2 = new Book("subcollection1book", Arrays.asList("Name 1", "Name 2"));
        collection1.addElement(book1);
        subcollection1.addElement(book2);

        JSONObject obj = new JSONObject(collection1.getStringRepresentation());
        Assertions.assertEquals(obj.get("name"), "collection 1");
        Assertions.assertEquals(obj.getJSONArray("elements").length(), 3);
        Assertions.assertEquals((obj.getJSONArray("elements").getJSONObject(0).getString("name")), "subcollection 1");
        Assertions.assertEquals((obj.getJSONArray("elements").getJSONObject(1).get("name")), "subcollection 2");
        Assertions.assertEquals((obj.getJSONArray("elements").getJSONObject(2).get("title")), "collection1book");
        Assertions.assertEquals((obj.getJSONArray("elements").getJSONObject(0)).getJSONArray("elements").getJSONObject(0).get("title"), "subcollection1book");
    }

    @Test
    public void testRestoreCollection(){
        Collection collection1 = new Collection("collection 1");
        Collection subcollection1 = new Collection("subcollection 1");
        Collection subcollection2 = new Collection("subcollection 2");
        collection1.addElement(subcollection1);
        collection1.addElement(subcollection2);
        Book book1 = new Book("collection1book", Arrays.asList("Name 1", "Name 2"));
        Book book2 = new Book("subcollection1book", Arrays.asList("Name 1", "Name 2"));
        collection1.addElement(book1);
        subcollection1.addElement(book2);

        String strRep = collection1.getStringRepresentation();
        Collection restoredCollection = Collection.restoreCollection(strRep);
        Assertions.assertEquals(restoredCollection.getName(), "collection 1");
        Assertions.assertEquals(((Collection)restoredCollection.getElements().get(1)).getName(), "subcollection 2");
        Assertions.assertEquals(((Book)restoredCollection.getElements().get(2)).getTitle(), "collection1book");
        Assertions.assertEquals(((Book)((Collection)restoredCollection.getElements().get(0)).getElements().get(0)).getTitle(), "subcollection1book");
    }
    /*
     * TODO: add  test methods to achieve at least 80% statement coverage of Collection.
     * Each test method should have appropriate JUnit assertions to test a single behavior
     * of the class. You should not add arbitrary code to test methods to just increase coverage.
     */
}
