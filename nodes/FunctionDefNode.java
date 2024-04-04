package nodes;

import provided.JottTree;
import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

public class FunctionDefNode implements JottTree {
    private IdNode idNode;
    private FunctionDefParamsNode functionDefParamsNode;
    private FunctionReturnNode functionReturnNode;
    private FBodyNode fBodyNode;

    // Constructor
    public FunctionDefNode(IdNode idNode, FunctionDefParamsNode functionDefParamsNode, FunctionReturnNode functionReturnNode, FBodyNode fBodyNode) {
        this.idNode = idNode;
        this.functionDefParamsNode = functionDefParamsNode;
        this.functionReturnNode = functionReturnNode;
        this.fBodyNode = fBodyNode;
    }

    public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", null);
        }
    
        // Look for Def
        Token defToken = tokens.remove(0);
        if (!defToken.getToken().equals("Def")) {
            throw new SyntaxErrorException("Expected Def but got: " + defToken.getToken(), defToken);
        }
    
        // Look for <id>
        IdNode idNode = IdNode.parseId(tokens);

        String idString = idNode.toString();

        // Look for [
        Token lbToken = tokens.remove(0);
        if(lbToken.getTokenType() != TokenType.L_BRACKET){
            throw new SyntaxErrorException("Expected left square bracket but got " + lbToken.getToken(), lbToken);
        }

        FunctionDefParamsNode functionDefParamsNode = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens, idString);

        // Look for ]
        Token rbToken = tokens.remove(0);
        if(rbToken.getTokenType() != TokenType.R_BRACKET){
            throw new SyntaxErrorException("Expected right square bracket but got " + rbToken.getToken(), rbToken);
        }

        // Look for :
        Token colonToken = tokens.remove(0);
        if(colonToken.getTokenType() != TokenType.COLON) {
            throw new SyntaxErrorException("Expected colon but got " + colonToken.getToken(), colonToken);
        }

        FunctionReturnNode functionReturnNode = FunctionReturnNode.parseFunctionReturnNode(tokens, idString);

        // Look for {
        Token lbraceToken = tokens.remove(0);
        if(lbraceToken.getTokenType() != TokenType.L_BRACE){
            throw new SyntaxErrorException("Expected left brace but got " + lbraceToken.getToken(), lbraceToken);
        }

        FBodyNode fBodyNode = FBodyNode.parseFBodyNode(tokens, idString);

        // Look for }
        Token rbraceToken = tokens.remove(0);
        if(rbraceToken.getTokenType() != TokenType.R_BRACE){
            throw new SyntaxErrorException("Expected right brace but got " + rbraceToken.getToken(), rbraceToken);
        }

        return new FunctionDefNode(idNode, functionDefParamsNode, functionReturnNode, fBodyNode);
    }    

    @Override
    public String convertToJott() {
        return "Def " + idNode.convertToJott() + "[" + functionDefParamsNode.convertToJott() + "]:" + functionReturnNode.convertToJott() + "{" + fBodyNode.convertToJott() + "}";
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
    public boolean validateTree() throws SemanticErrorException {

        // If the func id already exists, throw a semantic error
        
        if(JottParser.symTable.funcSymTab.containsKey(idNode.toString())) {
            throw new SemanticErrorException("Duplicate function " + idNode.toString(), idNode.getToken());
        }

        JottParser.symTable.funcSymTab.put(idNode.toString(), new ArrayList<>());

        functionDefParamsNode.validateTree();

        functionReturnNode.validateTree();

        if(idNode.toString().equals("main") && !functionReturnNode.getReturnType().equals("Void")) {
            throw new SemanticErrorException("Main must be of return Void", idNode.getToken());
        }

        fBodyNode.validateTree();

        return true;


        // // rest of validation still needed, this is just for the return type validating
        // this.fBodyNode.validateTree();
        // ArrayList<String> funcTab = JottParser.symTable.funcSymTab.get(this.idNode.toString());
        // String returnType = funcTab.get(funcTab.size()-1);

        // if(!(returnType.equals(this.fBodyNode.getReturnType()))){
        //     throw new SemanticErrorException("Mismatch return type for function " + this.idNode.toString(), this.fBodyNode.getReturnToken());
        // }
        
        // return true;
    }
    
}
