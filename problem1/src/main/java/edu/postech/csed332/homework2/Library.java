package edu.postech.csed332.homework2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;

import org.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * A container class for all collections (that eventually contain all
 * books). A library can be exported to or imported from a JSON file.
 */
public final class Library {
    private List<Collection> collections;

    /**
     * Builds a new, empty library.
     */
    public Library() {
        collections = new ArrayList<>();
    }

    /**
     * Builds a new library and restores its contents from a file.
     *
     * @param fileName the file from where to restore the library.
     */
    public Library(String fileName) {
        ArrayList<Collection> tempCollections = new ArrayList<>();
        try
        {
            JSONObject jsonLibrary = new JSONObject(new FileReader(fileName));
            for (int i=0; i<jsonLibrary.getJSONArray("collections").length();i++){
                tempCollections.add(Collection.restoreCollection(jsonLibrary.getJSONArray("collection").get(i).toString()));
            }
        }
        catch(FileNotFoundException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}
    }

    /**
     * Saves the contents of the library to the given file.
     *
     * @param fileName the file where to save the library
     */
    public void saveLibraryToFile(String fileName) {
        JSONObject jsonLibrary = new JSONObject();
        JSONArray jsonCollections = new JSONArray();
        for (int i=0; i<collections.size(); i++){
            jsonCollections.put(collections.get(i).getStringRepresentation());
        }
        jsonLibrary.put("collections", jsonCollections);

        try(FileWriter writer = new FileWriter(fileName))
        {
            writer.write(jsonLibrary.toString());
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the set of all books that belong to the collections
     * of a given name. Note that different collections may have the
     * same name. Return null if there is no collection of the
     * given name, and the empty set of there are such collections but
     * all of them are empty. For example, suppose that
     * - "Computer Science" is a top collection.
     * - "Operating Systems" is a collection under "Computer Science".
     * - "Linux Kernel" is a book under "Operating System".
     * - "Software Engineering" is a collection under "Computer Science".
     * - "Software Design Methods" is a bool under "Software Engineering".
     * Then, the findBooks method for "Computer Science" returns the set
     * of two books "Linux Kernel" and "Software Design Methods".
     *
     * @param collection a collection name
     * @return a set of books
     */
    public Set<Book> findBooks(String collection) {
        HashSet<Book> books = new HashSet<>();
        int i=0;
        boolean foundCollection = false;
        Collection thisCollection = new Collection("");
        while ((foundCollection==false) && (i<this.getCollections().size())){
            if (this.getCollections().get(i).getName().equals(collection)){
                foundCollection=true;
                thisCollection = this.getCollections().get(i);
                i++;
            }
        }
        if ((foundCollection==false) && (i==this.getCollections().size()-1)){
            return null;
        }
        else {
            for (i = 0; i < thisCollection.getElements().size(); i++){
                if (thisCollection.getElements().get(i) instanceof Book){
                    books.add((Book)thisCollection.getElements().get(i));
                    i++;
                }
                else if (thisCollection.getElements().get(i) instanceof Collection){
                    books.addAll(findBooks(((Collection)thisCollection.getElements().get(i)).getName()));
                }
            }
        }
        return books;
    }

    /**
     * Return the set of all books written by a given author in this
     * collection (including the sub-collections). Return the empty
     * set if there is no book written by the author. Note that a book
     * may involve multiple authors.
     *
     * @param author an author
     * @return Return the set of books written by the given author
     */
    public Set<Book> findBooksByAuthor(String author) {
        HashSet<Book> authorBooks = new HashSet<>();
        for(int i=0; i<this.getCollections().size();i++){
            Collection currentCollection = this.getCollections().get(i);
            HashSet<Book> currentCollectionBooks = (HashSet)findBooks(currentCollection.getName());
            Iterator<Book> bookIterator = currentCollectionBooks.iterator();
            while (bookIterator.hasNext()){
                Book currentBook = bookIterator.next();
                if (currentBook.getAuthors().contains(author)){
                    authorBooks.add(currentBook);
                }
            }
        }
        return authorBooks;
    }

    /**
     * Returns the collections contained in the library.
     *
     * @return library contained elements
     */
    public List<Collection> getCollections() {
        return collections;
    }
}
