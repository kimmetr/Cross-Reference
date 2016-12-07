package com.company;

import java.util.ArrayList;
import java.util.List;


public class Document {
    private String id;
    private List<Word> wordList;

    public Document(String id) {
        this.id = id;
        wordList = new ArrayList<>();
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void displayWordList() {
        for (int i = 0; i < wordList.size(); i++) {
            System.out.println(wordList.get(i).getWord());
        }
    }

    public void addWord(Word word) { //cross references word to document
        wordList.add(word);
        word.addDocument(this);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}