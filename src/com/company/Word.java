package com.company;

import java.util.ArrayList;
import java.util.List;


public class Word implements Comparable {
    private String stem;
    private List<Document> docList;

    public Word(String stem) {
        this.stem = stem;
        docList = new ArrayList<>();
    }

    public void addDocument(Document document) {
        docList.add(document);
    }

    public List<Document> getDocList() { //returns the list
        return docList;
    }

    public void readDocumentList() { //reads contents of list
        for (int i = 0; i < docList.size(); i++) {
            System.out.println(docList.get(i).toString());
        }
    }


    public String getWord() {
        return stem;
    }

    @Override
    public String toString() {
        return stem;
    }

    @Override
    public int compareTo(Object object) { //allows for tree creation
        Word word = (Word) object;
        if (this.stem.compareTo(word.getWord()) > 0) {
            return 1;
        } else if (this.stem.compareTo(word.getWord()) < 0) {
            return -1;
        }
        return 0;
    }
}