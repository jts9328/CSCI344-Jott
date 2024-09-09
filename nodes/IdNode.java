package nodes;

import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author Gian
 **/

public class IdNode implements OperandNode {
    private Token token;

    // ID Constructor
    public IdNode(Token token) {
        this.token = token;
    }

    /**
     * Parses a id node given the list of remaining tokens
     * 
     * @param tokens arraylist of tokens to parse through
     * @return IdNode
     * @throws SyntaxErrorException
     */
    public static IdNode parseId(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", null);
        }

        Token token = tokens.remove(0);

        // Check if the first token is of type ID
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            // Create an IdNode with the token's value
            IdNode node = new IdNode(token);
            return node;
        } else {
            // Token is not an identifier; handle error or return null
            throw new SyntaxErrorException("Expected ID, found: " + token.getToken(), token);
        }
    } 

    public String getReturnType() {
        return JottParser.symTable.varSymTab.get(this.token.getToken())[0];
    } 

    @Override
    public String convertToJott() {
        // Simply return the identifier's value for the Jott code representation
        return this.token.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.token.getToken();
    }

    @Override
    public String convertToC() {
        return this.token.getToken();
    }

    @Override
    public String convertToPython(int tabs) {
        return this.token.getToken();
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    public Token getToken() {
        return token;
    }

    public String toString() {
        return this.token.getToken();
    }
    
}
