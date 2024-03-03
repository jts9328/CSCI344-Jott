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
    private Token token;
    private Token sign;

    // NUMBER Constructors
    public NumNode(Token token) {
        this.token = token;
        this.sign = null;
    }
    public NumNode(Token token, Token sign){
        this.token = token;
        this.sign = sign;
    }
    

    public static NumNode parseNum(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (!tokens.isEmpty()) {
            Token token = tokens.remove(0);

            // Check if the first token is of type NUMBER
            if (token.getTokenType() == TokenType.NUMBER) {
                // Create an NumNode with the token's value
                NumNode node = new NumNode(token);
                return node;
            } else if(token.getTokenType() == TokenType.MATH_OP && !tokens.isEmpty()){
                Token sign = tokens.remove(0);
                NumNode node = new NumNode(token,sign);
                return node;
            }else  {
                // Token is not an identifier; handle error or return null
                throw new SyntaxErrorException("Expected Number in" + token.getFilename() + " at " + token.getLineNum() + ", found: " + token.getTokenType(), token);
            }
        }
        else throw new SyntaxErrorException("Unexpected End of File", JottParser.lastToken);
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
