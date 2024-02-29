package nodes;

import provided.JottTree;
import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

public class ElseNode implements JottTree{
    private BodyNode body;

    // Else Statement Constructor
    public ElseNode(BodyNode body) {
        this.body = body;
    }

    // Else Null Constructor
    public ElseNode() {
        this.body = null;
    }

    public static ElseNode parseElse(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("No tokens available for parsing.", null);
        }

        Token token = tokens.get(0);

        //check if there is an else statement
        if (token.getToken().equals("Else")) {
            token = tokens.remove(0); //remove else token
            //parse body
            BodyNode body = BodyNode.parseBodyNode(tokens);
            return new ElseNode(body);
        } else {
            // Token is not an identifier; handle error or return null
            throw new SyntaxErrorException("Expected Else in" + token.getFilename() + " at " + token.getLineNum() + ", found: " + token.getToken(), token);
        }
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
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
