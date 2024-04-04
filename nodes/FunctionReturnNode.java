package nodes;


import java.util.ArrayList;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionReturnNode implements JottTree {

    private TypeNode typeNode;
    private Token voidToken;
    private String funcId;

    /**
     * Grammar: < function_return > --> < type > | Void
     * 
     * @param typeNode  typeNode
     * @param voidToken void keyword token
     */
    public FunctionReturnNode(TypeNode typeNode, Token voidToken, String funcId) {
        this.typeNode = typeNode;
        this.voidToken = voidToken;
        this.funcId = funcId;
    }

    /**
     * Parses a function return node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      FunctionReturnNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static FunctionReturnNode parseFunctionReturnNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        Token token = tokens.get(0); 

        if (token.getTokenType() == TokenType.ID_KEYWORD && token.getToken().equals("Void")) {
            tokens.remove(0);
            return new FunctionReturnNode(null, token, funcId);
        } 
        
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        
        return new FunctionReturnNode(typeNode, null, funcId);
    }

    @Override
    public String convertToJott() {
        if(typeNode == null) {
            return voidToken.getToken();
        } else {
            return typeNode.convertToJott();
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
        String returnType = "Void";
        if(typeNode != null) {
            returnType = typeNode.toString();
        }

        JottParser.symTable.funcSymTab.get(funcId).add(returnType);
        
        return true;
    }

    public String getReturnType() {
        if(typeNode != null) {
            return typeNode.toString();
        }
        return "Void";
    }
    
}
