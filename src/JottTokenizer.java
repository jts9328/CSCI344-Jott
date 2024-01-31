package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author Group 6
 **/

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename) {
        ArrayList<Token> tokens = new ArrayList<>();
        int lineNum = 0;
		try {
            File jottFile = new File(filename);
            Scanner scanner = new Scanner(jottFile);
            while (scanner.hasNextLine()) {
                lineNum++;
                String data = scanner.nextLine();
                char[] characters = data.toCharArray();
                int index = 0;
                while(index < characters.length) {
                    // Giant if else

                    // Colon & Function Header
                    if(characters[index] == ':'){
                        index++;
                        if(index < characters.length && characters[index] == ':'){
                            tokens.add(new Token("::", filename, lineNum, TokenType.FC_HEADER));
                        } else {
                            tokens.add(new Token(":", filename, lineNum, TokenType.COLON));
                            index++;
                        }
                    }
                }             
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return tokens;
	}

}