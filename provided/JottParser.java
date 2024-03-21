package provided;
import nodes.ProgramNode;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.SemanticErrorException;
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
            HashMap<String, String> varSymTab = new HashMap<>();
            HashMap<String, String> funcSymTab = new HashMap<>();
            return ProgramNode.parseProgram(tokens, varSymTab, funcSymTab);
        } catch (SyntaxErrorException e) {
            e.printErrorMessage();
            return null;
        }
        // TODO uncomment when this exception is thrown at some point
        // catch (SemanticErrorException e) {
        //     e.printErrorMessage();
        //     return null;
        // }
    }
}
