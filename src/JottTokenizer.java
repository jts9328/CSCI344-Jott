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
        ArrayList<Token> tokens = new ArrayList<>();
		try {
            File jottFile = new File(filename);
            Scanner scanner = new Scanner(jottFile);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                char[] characters = data.split();

                tokens = commentsBracketsSemicolon(tokens);

                tokens = number(tokens);

                tokens = operators(tokens);

                tokens = idKeywordHeader(tokens);

                tokens = string(tokens);                
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
	}

    public static ArrayList<Token> commentsBracketsSemicolon(ArrayList<Token> tokens) {
        return tokens;
    }

    public static ArrayList<Token> number(ArrayList<Token> tokens) {
        return tokens;
    }

    public static ArrayList<Token> operators(ArrayList<Token> tokens) {
        return tokens;
    }

    public static ArrayList<Token> idKeywordHeader(ArrayList<Token> tokens) {
        return tokens;
    }

    public static ArrayList<Token> string(ArrayList<Token> tokens) {
        return tokens;
    }
}