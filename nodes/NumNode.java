package nodes;

import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

/**
 * This class represents the number node in the parse tree
 * 
 * @author Gian
 **/

public class NumNode implements OperandNode {
    private Token number;
    private Token sign;

    // NUMBER Constructors
    public NumNode(Token first) {
        this.number = first;
        this.sign = null;
    }
    public NumNode(Token first, Token second){
        this.number = second;
        this.sign = first;
    }
    

    public static NumNode parseNum(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token first = tokens.remove(0);

        // Check if the first token is of type NUMBER
        if (first.getTokenType() == TokenType.NUMBER) {
            // Create an NumNode with the token's value
            NumNode node = new NumNode(first);
            return node;
        } else if(first.getTokenType() == TokenType.MATH_OP && !tokens.isEmpty()){
            Token second = tokens.remove(0);
            NumNode node = new NumNode(first,second);
            return node;
        }else  {
            // Token is not an identifier; handle error or return null
            throw new SyntaxErrorException("Expected Number, found: " + first.getTokenType(), first);
        }
    }

    @Override
    public String convertToJott() {
        // Simply return the identifier's value for the Jott code representation
        if (sign==null){
            return this.number.getToken();
         } else {
            return this.sign.getToken() + this.number.getToken();

        }
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
