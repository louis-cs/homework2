package edu.postech.csed332.homework2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * The Collection class represents a single collection, which contains
 * a name of the collection and also has a list of references to every
 * book and every subcollection in the collection. A collection can
 * also be exported to and imported from a JSON string representation.
 */
public final class Collection extends Element {
    private List<Element> elements;
    private String name;

    /**
     * Creates a new collection with the given name.
     *
     * @param name the name of the collection
     */
    public Collection(String name) {
        this.name = name;
        elements = new ArrayList<>();
    }

    /**
     * Restores a collection from its string representation in JSON
     *
     * @param stringRepr the string representation
     */
    public static Collection restoreCollection(String stringRepr) {
        String newName;
        ArrayList<Element> newElements = new ArrayList<>();

        JSONObject jsonCollection = new JSONObject(stringRepr);
        newName = jsonCollection.getString("name");
        JSONArray jsonElements = jsonCollection.getJSONArray("elements");

        for (int i=0; i<jsonElements.length();i++){
            if ((((JSONObject)jsonElements.get(i)).has("title"))){
                newElements.add(new Book(jsonElements.get(i).toString()));
            }
            if ((((JSONObject)jsonElements.get(i)).has("name"))){
                newElements.add(restoreCollection(jsonElements.get(i).toString()));
            }
        }
        Collection newCollection = new Collection(newName);
        for (int i=0;i<newElements.size();i++){
            newCollection.addElement(newElements.get(i));
        }
        return newCollection;
    }

    /**
     * Returns the JSON string representation of this collection, which
     * contains the name of this collection and all elements (books and
     * subcollections) contained in the collection.
     *
     * @return string representation of this collection
     */
    public String getStringRepresentation() {
        JSONObject jsonCollection = new JSONObject();
        jsonCollection.put("name", this.name);
        JSONArray jsonElements = new JSONArray();
        for (int i=0; i<elements.size();i++){
            if (elements.get(i) instanceof Book){
                JSONObject bo = new JSONObject(((Book) elements.get(i)).getStringRepresentation());
                jsonElements.put(bo);
                //jsonElements.put(((Book) elements.get(i)).getStringRepresentation());
            }
            else if (elements.get(i) instanceof Collection){
                JSONObject bo = new JSONObject(((Collection) elements.get(i)).getStringRepresentation());
                jsonElements.put(bo);
                //jsonElements.put(((Collection) elements.get(i)).getStringRepresentation());
            }
        }
        jsonCollection.put("elements", jsonElements);

        return jsonCollection.toString();
    }

    /**
     * Adds an element to this collection, if the element has no parent
     * collection yet. Otherwise, this method returns false, without
     * actually adding the element to this collection.
     *
     * @param element the element to add
     * @return true on success, false on fail
     */
    public boolean addElement(Element element) {
        boolean noParent = false;
        if (element.getParentCollection()==null){
            noParent=true;
            elements.add(element);
            element.setParentCollection(this);
        }
        return noParent;
    }

    /**
     * Deletes an element from the collection. Returns false if the
     * collection does not have this element. Hint: do not forget to
     * clear parentCollection of given element.
     *
     * @param element the element to remove
     * @return true on success, false on fail
     */
    public boolean deleteElement(Element element) {
        boolean foundElement = false;
        if (elements.contains(element)){
            foundElement=true;
            element.setParentCollection(null);
            elements.remove(element);
        }
        return foundElement;
    }

    /**
     * Returns the name of the collection.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of elements.
     *
     * @return the list of elements
     */
    public List<Element> getElements() {
        return elements;
    }
}
