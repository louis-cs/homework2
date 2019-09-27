package edu.postech.csed332.homework2;

import java.util.ArrayList;
import java.util.List;
import org.json.*;

/**
 * A book contains the title and the author(s), each of which is
 * represented as a string. A book can be exported to and import
 * from a string representation in JSON (https://www.json.org/).
 * Using the string, you should be able to construct a book object.
 */
public final class Book extends Element {
    private String title;
    private List<String> authors;

    /**
     * Builds a book with the given title and author(s).
     *
     * @param title   the title of the book
     * @param authors the author(s) of the book
     */
    public Book(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
        // TODO write more code if necessary
    }

    /**
     * Builds a book from the string representation in JSON, which
     * contains the title and author(s) of the book.
     *
     * @param stringRepr the JSON string representation
     */
    public Book(String stringRepr) {
        String tempTitle;
        List<String> tempAuthors = new ArrayList<>();
        JSONObject jsonBook = new JSONObject((stringRepr));
        tempTitle=jsonBook.getString("title");
        for (int i=0; i<jsonBook.getJSONArray("authors").length(); i++){
            tempAuthors.add(jsonBook.getJSONArray("authors").getString(i));
        }
        this.title=tempTitle;
        this.authors=tempAuthors;
    }

    /**
     * Returns the JSON string representation of this book. The string
     * representation contains the title and author(s) of the book.
     *
     * @return the string representation
     */
    public String getStringRepresentation() {
        JSONObject jsonBook = new JSONObject();
        jsonBook.put("title", this.title);
        JSONArray jsonAuthors = new JSONArray();
        for (int i=0; i<authors.size();i++){
            jsonAuthors.put(authors.get(i));
        }
        jsonBook.put("authors", jsonAuthors);

        return jsonBook.toString();
    }

    /**
     * Returns all the collections that this book belongs to directly
     * and indirectly, starting from the bottom-level collection.
     * <p>
     * For example, suppose that "Computer Science" is a top collection,
     * "Operating Systems" is a collection under "Computer Science", and
     * "Linux Kernel" is a book under "Operating System". Then, this
     * method for "The Linux Kernel" returns the list of the collections
     * (Operating System, Computer Science), starting from the bottom.
     *
     * @return the list of collections
     */
    public List<Collection> getContainingCollections() {
        ArrayList<Collection> fatherCollections = new ArrayList<>();
        Collection aux = this.getParentCollection();
        while (aux!=null){
            fatherCollections.add(aux);
            aux=aux.getParentCollection();
        }
        return fatherCollections;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author(s) of the book.
     *
     * @return the author(s)
     */
    public List<String> getAuthors() {
        return authors;
    }
}