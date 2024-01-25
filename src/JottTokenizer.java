package provided;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author Group 6
 **/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename) {
		try {
            File jottFile = new File(filename);
            Scanner scanner = new Scanner(jottFile);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                char[] characters = data.split();

                commentsBracketsSemicolon();

                number();

                operators();

                idKeywordHeader();

                string();                
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}

    public static void commentsBracketsSemicolon() {

    }

    public static void number() {

    }

    public static void operators() {

    }

    public static void idKeywordHeader() {

    }

    public static void string() {

    }
}