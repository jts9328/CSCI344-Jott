package provided;
import nodes.ProgramNode;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;

import exceptions.SyntaxErrorException;

public class JottParser {

    public static Token lastToken;

    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens){
        try {
            if(!tokens.isEmpty()) {
                lastToken = tokens.get(tokens.size() - 1);
            }

            return ProgramNode.parseProgram(tokens);
        } catch (SyntaxErrorException e) {
            System.err.println("Syntax Error:\n" + e.getMessage() + "\n" + e.getFilename() + ":" + e.getLineNumber());
            return null;
        }
    }

    /**
     * Checks if the tokens arraylist is empty and throws a syntax error if so.
     * Should be called after a tokens.remove(0) is done.
     * 
     * @param errorMessage          error message to display
     * @param lastToken             last token before empty
     * @param tokens                tokens arraylist
     * @throws SyntaxErrorException throws a syntax error if empty
     */
    // public static void checkEmpty(String errorMessage, Token lastToken, ArrayList<Token> tokens) throws SyntaxErrorException {
    //     if(tokens.isEmpty()) {
    //         throw new SyntaxErrorException(errorMessage, lastToken);
    //     }
    // }

    // /**
    //  * Prints error messages in a standard format
    //  * 
    //  * @param errorMessage  specific message for the error
    //  * @param fileName      file where error occured
    //  * @param lineNumber    line number where error occured
    //  */
    // public static void printError(String errorMessage, Token token) {
    //     System.err.println("Syntax Error:\n" + errorMessage + "\n" + token.getFilename() + ":" + token.getLineNum());
    // }
}
