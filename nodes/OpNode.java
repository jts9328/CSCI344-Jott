package nodes;

import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

/**
 * Operation node in the parse tree
 * 
 * @author Gian
 **/

public class OpNode implements ExprNode {
    private Token token;

    // ID Constructor
    public OpNode(Token token) {
        this.token = token;
    }

    public static OpNode parseOp(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        } 
        Token token = tokens.remove(0);

        // Check if the first token is of type REL_OP or MATH_OP
        if ((token.getTokenType() == TokenType.REL_OP) || token.getTokenType() == TokenType.MATH_OP) {
            // Create an OpNode with the token's value
            OpNode node = new OpNode(token);
            return node;
        } else {
            // Token is not an identifier; handle error or return null
            throw new SyntaxErrorException("Expected Relational Operator, found: " + token.getTokenType(), token);
        }
    }

    public String getReturnType() {
        //only rel and math ops
        return null;
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
        return this.token.getToken();
    }

    @Override
    public String convertToC() {
        return this.token.getToken();
    }

    @Override
    public String convertToPython(int tabs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() {
        return true;
    }

    
}
