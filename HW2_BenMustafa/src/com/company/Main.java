package com.company;

import Mars.GeneticTree;
import Mars.Martian;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Martian martian = new Martian();
        GeneticTree tree = new GeneticTree(martian);
        tree.TextToTree("/Users/anasbenmustafa/Desktop/text.txt");
    }
}
