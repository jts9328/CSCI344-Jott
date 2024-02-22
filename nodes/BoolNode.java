package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BoolNode implements JottTree{
    private Token boolToken;

    // Boolean Constructor
    public BoolNode(Token value) {
       this.boolToken = value;
    }

    public static BoolNode parseBoolNode(ArrayList<Token> tokens) {
        Token boolToken = tokens.remove(0);

        // Checks if the token type is ID_KEYWORD
        if(boolToken.getTokenType() != TokenType.ID_KEYWORD){
            return null; // Temp, Replace with exception
        }
        // IF keyword is "True" return valid BoolNode with token Value
        else if(boolToken.getToken().equals("True")){
            BoolNode node = new BoolNode(boolToken);
            return node;
        } 
        // IF keyword is "False" return valid BoolNode with token Value
        else if (boolToken.getToken().equals("False")){
            BoolNode node = new BoolNode(boolToken);
            return node;
        } 
        // Throw Exception since Token is not a valid Boolean Keyword
        else {
            return null; // Temp, Replace with exception
        }

    }

    @Override
    public String convertToJott() {
        return this.boolToken.getToken();
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
