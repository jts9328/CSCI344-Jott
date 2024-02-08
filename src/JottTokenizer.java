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
     * 
     * @param filename the name of the file to tokenize; can be relative or absolute
     *                 path
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
                while (index < characters.length) {
                    // Giant if else
                    // Whitespace
                    if (characters[index] == ' ') {
                        index++;
                    }
                    // Comments
                    else if (characters[index] == '#') {
                        while (index < characters.length && characters[index] != '\n') {
                            index++;
                        }
                        index++;
                    }
                    // Comma
                    else if (characters[index] == ',') {
                        tokens.add(new Token(",", filename, lineNum, TokenType.COMMA));
                        index++;
                    }
                    // Right Bracket
                    else if (characters[index] == ']') {
                        tokens.add(new Token("]", filename, lineNum, TokenType.R_BRACKET));
                        index++;
                    }
                    // Left Bracket
                    else if (characters[index] == '[') {
                        tokens.add(new Token("[", filename, lineNum, TokenType.L_BRACKET));
                        index++;
                    }
                    // Right Brace
                    else if (characters[index] == '}') {
                        tokens.add(new Token("}", filename, lineNum, TokenType.R_BRACE));
                        index++;
                    }
                    // Left Brace
                    else if (characters[index] == '{') {
                        tokens.add(new Token("{", filename, lineNum, TokenType.L_BRACE));
                        index++;
                    }
                    // Semicolon
                    else if (characters[index] == ';') {
                        tokens.add(new Token(";", filename, lineNum, TokenType.SEMICOLON));
                        index++;
                    }
                    // Colon & Function Header
                    else if (characters[index] == ':') {
                        index++;
                        if (index < characters.length && characters[index] == ':') {
                            tokens.add(new Token("::", filename, lineNum, TokenType.FC_HEADER));
                            index++;
                        } else {
                            tokens.add(new Token(":", filename, lineNum, TokenType.COLON));
                        }
                    }
                    // keyword/ID tokenizer
                    else if (Character.isLetter(characters[index])) {
                        System.out.print(characters[index]);
                        int startIndex = index;
                        index++;
                        while (index < characters.length
                                && (Character.isLetter(characters[index]) || Character.isDigit(characters[index]))) {
                            index++;
                        }
                        String ID_KeywordToken = data.substring(startIndex, index);
                        tokens.add(new Token(ID_KeywordToken, filename, lineNum, TokenType.ID_KEYWORD));
                    } 
                    // Digit
                    else if (Character.isDigit(characters[index]) || characters[index] == '.') {
                        int startIndex = index;
                        int decimalTrue = 0;
                        // if starts with .
                        if (characters[index] == '.') {
                            decimalTrue = 1;
                        }
                        index++;
                        // while a whole number (and first decimal)
                        while (index < characters.length && (Character.isDigit(characters[index]) || (characters[index] == '.' && decimalTrue == 0)) ) {
                            index++;
                            if ( index < characters.length && characters[index] == '.') {
                                decimalTrue = 1;
                                break;
                            }
                        }
                        if (decimalTrue == 1 && index < characters.length) {
                            // two decimal check
                            if (characters[index] == '.') {
                                System.err.println("Syntax Error\nInvalid Number: '..'" + filename + ":" + lineNum);
                                return null;
                            }
                            // accept decimal digits
                            while (index < characters.length && Character.isDigit(characters[index])) {
                                index++;
                            }
                        } else if(decimalTrue == 1){
                            System.err.println("Syntax Error\nInvalid Number: '.'" + filename + ":" + lineNum);
                            return null;
                        }
                        if (index <= characters.length) {
                            String token = data.substring(startIndex, index);
                            tokens.add(new Token(token, filename, lineNum, TokenType.NUMBER));
                        } else {
                            System.err.println("Syntax Error\nUnexpected Character in Number Token" + filename + ":" + lineNum);
                            return null;
                        }
                    }
                    // String Tokenization
                    else if (characters[index] == '\"') {
                        int startIndex = index;
                        index++;
                        // Find closing quote
                        while (index < characters.length && characters[index] != '\"') {
                            index++;
                        }
                        // Closing quote found, create token
                        if (index < characters.length) {
                            String stringToken = data.substring(startIndex, index + 1);
                            tokens.add(new Token(stringToken, filename, lineNum, TokenType.STRING));
                            index++;
                        }
                        // Handle error, no closing quote
                        else {
                            System.err.println("Syntax Error\nString literal expects following '\"'\n" + filename + ":" + lineNum);
                            return null;
                        }
                    }
                    // Math operations
                    else if (characters[index] == '+') {
                        tokens.add(new Token("+", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    else if (characters[index] == '-') {
                        tokens.add(new Token("-", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    else if (characters[index] == '*') {
                        tokens.add(new Token("*", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    else if (characters[index] == '/') {
                        tokens.add(new Token("/", filename, lineNum, TokenType.MATH_OP));
                        index++;
                    }
                    // assign and == operation
                    else if (characters[index] == '=') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token("==", filename, lineNum, TokenType.REL_OP));
                                index++;
                            } else {
                                tokens.add(new Token("=", filename, lineNum, TokenType.ASSIGN));
                            }
                        } else {
                            tokens.add(new Token("=", filename, lineNum, TokenType.ASSIGN));
                        }
                    }
                    // Relational Operations <>
                    else if (characters[index] == '<') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token("<=", filename, lineNum, TokenType.REL_OP));
                                index++;
                            } else {
                                tokens.add(new Token("<", filename, lineNum, TokenType.REL_OP));
                            }
                        } else {
                            tokens.add(new Token("<", filename, lineNum, TokenType.REL_OP));
                        }
                    }
                    else if (characters[index] == '>') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token(">=", filename, lineNum, TokenType.REL_OP));
                                index++;
                            } else {
                                tokens.add(new Token(">", filename, lineNum, TokenType.REL_OP));
                            }
                        } else {
                            tokens.add(new Token(">", filename, lineNum, TokenType.REL_OP));
                        }
                    }
                    // Relational Operation !=
                    else if (characters[index] == '!') {
                        index++;
                        if (index < characters.length) {
                            if (characters[index] == '=') {
                                tokens.add(new Token("!=", filename, lineNum, TokenType.REL_OP));
                                index++;
                            } else {
                                System.err.println("Syntax Error\nExpecting = after !\n" + filename + ":" + lineNum);
                                return null;
                            }
                        } else {
                            System.err.println("Syntax Error\nExpecting = after !\n" + filename + ":" + lineNum);
                            return null;
                        }
                    } else {
                        System.out.println("Error: Unexcepted token " + characters[index] + " on line " + lineNum);
                        index++;
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