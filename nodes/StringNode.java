package nodes;

import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

/**
 * This class represents the string node in the parse tree
 * 
 * @author Gian
 **/

public class StringNode implements ExprNode {
    private Token token;

    // String Constructor
    public StringNode(Token token) {
        this.token = token;
    }

    public static StringNode parseString(ArrayList<Token> tokens) throws SyntaxErrorException {
        if(tokens.isEmpty()){
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token token = tokens.remove(0);

        // Check if the first token is of type NUMBER
        if (token.getTokenType() == TokenType.STRING) {
            // Create an StringNode with the token's value
            StringNode node = new StringNode(token);
            return node;
        } else {
            // Token is not an identifier; handle error or return null
            throw new SyntaxErrorException("Expected Number, found: " + token.getTokenType(), token);
        }
    }

    public String getReturnType() {
        return "String";
    }

    public Token getToken() {
        return this.token;
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
        return true;
    }
    
}
