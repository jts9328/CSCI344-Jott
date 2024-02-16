package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IdNode implements JottTree {
    private Token token;

    // ID Constructor
    public IdNode(Token token) {
        this.token = token;
    }

    public static IdNode parseId(ArrayList<Token> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            return null; // No tokens to parse
        }

        Token token = tokens.get(0);

        // Check if the first token is of type ID
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            // Create an IdNode with the token's value
            IdNode node = new IdNode(token);
            return node;
        } else {
            // Token is not an identifier; handle error or return null
            System.err.println("Expected ID, found: " + token.getTokenType());
            return null;
        }
    }

    @Override
    public String convertToJott() {
        // Simply return the identifier's value for the Jott code representation
        return this.token.getToken();
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToC'");
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
