package nodes;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class TypeNode implements JottTree {

    private Token token;

    /**
     * Grammar: < type > --> Double | Integer | String | Boolean
     * 
     * @param token keyword token
     */
    public TypeNode(Token token) {
        this.token = token;
    }

    /**
     * Parses a params node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      TypeNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static TypeNode parseTypeNode(ArrayList<Token> tokens) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        Token token = tokens.remove(0);

        // Check if the first token is of type ID_KEYWORD and is one of the expected types
        if ((token.getTokenType() == TokenType.ID_KEYWORD) &&
            (token.getToken().equals("Double") ||
             token.getToken().equals("Integer") ||
             token.getToken().equals("String") ||
             token.getToken().equals("Boolean")
            )) {

            return new TypeNode(token);
        } else {
            throw new SyntaxErrorException(token.getToken() + " cannot be resolved to a type", token);
        }
    }

    public Token getToken() {return token;}

    @Override
    public String convertToJott() {
        return token.getToken();
    }

    @Override
    public String convertToJava(String className) {
        switch(token.getToken()) {
            case "Double": return "double";
            case "Integer": return "int";
            case "String": return "String";
            case "Boolean": return "boolean";
            default: return "TypeNode convertToJava error";
        }
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

    public String toString(){
        return this.token.getToken();
    }
    
}
