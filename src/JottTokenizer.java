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
                    //Math operations
                    if (characters[index] == '+') {
                        tokens.add(new Token("+", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    if (characters[index] == '-') {
                        tokens.add(new Token("-", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    if (characters[index] == '*') {
                        tokens.add(new Token("*", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    if (characters[index] == '/') {
                        tokens.add(new Token("/", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    //assign and == operation
                    if (characters[index] == '=') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token("==", filename, lineNum, TokenType.REL_OP));
                                index++;
                            }
                            else {
                                tokens.add(new Token("=", filename, lineNum, TokenType.ASSIGN));
                            }
                        }
                        else {
                            tokens.add(new Token("=", filename, lineNum, TokenType.ASSIGN));
                        }
                    }
                    //Relational Operations <>
                    if (characters[index] == '<') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token("<=", filename, lineNum, TokenType.REL_OP));
                                index++;
                            }
                            else {
                                tokens.add(new Token("<", filename, lineNum, TokenType.REL_OP));
                            }
                        }
                        else {
                            tokens.add(new Token("<", filename, lineNum, TokenType.REL_OP));
                        }
                    }
                    if (characters[index] == '>') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token(">=", filename, lineNum, TokenType.REL_OP));
                                index++;
                            }
                            else {
                                tokens.add(new Token(">", filename, lineNum, TokenType.REL_OP));
                            }
                        }
                        else {
                            tokens.add(new Token(">", filename, lineNum, TokenType.REL_OP));
                        }
                    }
                    //Relational Operation != 
                    if (characters[index] == '!') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token("!=", filename, lineNum, TokenType.REL_OP));
                                index++;
                            }
                            else {
                                System.out.println("Error: Expecting = after ! " + lineNum);
                                break;
                            }
                        }
                        else {
                            Sytem.out.println("Error: Expecting = after ! " + lineNum);
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
