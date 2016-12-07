package com.company;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;

public class CrossReference {
    //initiate lists
    private static List<Word> wordList;
    private static List<Document> documentList;
    private static List<Document> activeDocumentList;
    private static List<Word> activeWordList;


    //methods for reading in files
    private static void readWordsFromFile(String fileName) throws Exception {
        wordList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String wordLine = br.readLine();
        while (wordLine != null) {
            wordList.add(new Word(wordLine));
            wordLine = br.readLine(); //moves to next line
        }
    }

    private static void readDocsFromFile(String fileName) throws Exception {
        documentList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String documentLine = br.readLine();
        while (documentLine != null) {
            String[] documentLineSplit = documentLine.split(" ");
            String documentID = documentLineSplit[0];
            Document document = new Document(documentID);
            for (int i = 1; i < documentLineSplit.length; i++) {
                document.addWord(wordList.get(Integer.parseInt(documentLineSplit[i]) - 1)); //reads in actual words to document wordList and cross references
            }
            documentList.add(document);
            documentLine = br.readLine(); //moves to next line
        }
    }


    public static void main(String[] args) throws Exception {
        //reads in appropriate files and cross references them
        readWordsFromFile(args[0]);
        readDocsFromFile(args[1]);


        //used words are added to tree
        TreeMap<String, Word> usedWordsTree = new TreeMap<>();
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).getDocList().size() > 0) { //only uses words that have document references
                usedWordsTree.put(wordList.get(i).getWord(), wordList.get(i)); //since wordList is already alphabetized indexes are used here as well
            }
        }


        //user interface
        Scanner input = new Scanner(System.in);
        String[] inputSplit = {"", ""};

        while (input.hasNext()) {
            System.out.println("Please enter a command and object:");
            System.out.println("wordlist 'document id'");
            System.out.println("doclist 'word'");
            System.out.println("occurswith 'word'");
            System.out.println("quit");
            System.out.println();

            inputSplit = input.nextLine().split(" ");
            String id = inputSplit[0];
            String object = inputSplit[1];

            if (id.equals("wordlist")) {
                for (int i = 0; i < documentList.size(); i++) {
                    if (documentList.get(i).getId().equals(object)) {
                        if (documentList.get(i).getWordList().size() == 0) {
                            System.out.println("The document selected has no words.");
                        }
                        documentList.get(i).displayWordList();
                    } else {
                        System.out.println("The document selected does not exist.");
                    }
                }
            } else if (id.equals("doclist")) {
                if (usedWordsTree.get(object) != null) { //finds object in the tree
                    usedWordsTree.get(object).readDocumentList();
                } else {
                    System.out.print("The word selected is not within any document.");
                }

            } else if (id.equals("occurswith")) {
                if (usedWordsTree.get(object) != null) {
                    activeDocumentList = usedWordsTree.get(object).getDocList();
                    activeWordList = new ArrayList<>();
                    for (Document d : activeDocumentList) {
                        for (Word w : d.getWordList()) {
                            if (!activeWordList.contains(w)) {
                                activeWordList.add(w);
                            }
                        }
                    }
                    for (Word w : activeWordList) {
                        System.out.println(w.toString());
                    }
                } else {
                    System.out.print("The word selected is not within any document.");
                }
            } else if (id.equals("quit")) {
                return;
            }
            System.out.println();
        }
    }
}