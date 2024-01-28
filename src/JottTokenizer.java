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
                    // String Tokenization
                    if(characters[index] == '\"'){
                        int startIndex = index;
                        index++;
                        // Find closing quote
                        while(index < characters.length && characters[index] != '\"'){
                            index++;
                        }
                        // Closing quote found, create token
                        if (index < characters.length){
                            String stringToken = data.substring(startIndex, index + 1);
                            tokens.add(new Token(stringToken, filename, lineNum, TokenType.STRING));
                            index++;
                        }
                        // Handle error, no closing quote
                        else{
                            System.out.println("Error: String literal not closed on line " + lineNum);
                            break;
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