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
                    if (Character.isDigit(characters[index])){
                        int startIndex = index;
                        int flag = 0;
                        index++;
                        while(Character.isDigit(characters[index]) || characters[index] == '.'){
                            index++;
                            if(characters[index] == '.'){
                                flag = 1;
                                break;
                            }
                        if(flag == 1){
                            while(index < characters.length || Character.isDigit(characters[index])){
                                index++;
                            }
                        }
                        if (index < characters.length){
                            String token = data.substring(startIndex, index + 1);
                            tokens.add(new Token(token, filename, lineNum, TokenType.NUMBER));
                            index++;
                        }
                        else{
                            System.out.println("Error: Number token error" + lineNum);
                            break;
                        }
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