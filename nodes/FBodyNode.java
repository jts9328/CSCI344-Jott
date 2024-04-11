package nodes;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FBodyNode implements JottTree{

    private BodyNode bodyNode;
    private ArrayList<VarDecNode> varDecs = new ArrayList<VarDecNode>();
    private String funcId;

    public FBodyNode(BodyNode bodyNode, ArrayList<VarDecNode> varDecs, String funcId){
        this.bodyNode = bodyNode;
        this.varDecs = varDecs;
        this.funcId = funcId;
    }

    public static FBodyNode parseFBodyNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        // EOF
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token token = tokens.get(0);
        ArrayList<VarDecNode> varDecs = new ArrayList<VarDecNode>();
        // loop until its not a variable declaration
        while((token.getTokenType() == TokenType.ID_KEYWORD && 
            (token.getToken().equals("Integer") || token.getToken().equals("Double") || 
            token.getToken().equals("String") || token.getToken().equals("Boolean")))){
            varDecs.add(VarDecNode.parseVarDecNode(tokens, funcId));
            token = tokens.get(0);
        }
        // checks and exception handling should be caught by parseBodyNode
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens, funcId);
        return new FBodyNode(bodyNode, varDecs, funcId);
    }

    public Token getReturnToken(){
        return this.bodyNode.getReturnToken();
    }

    @Override
    public String convertToJott() {
        String jott = "";
        // if no variable declarations, should break out for loop
        for(VarDecNode varDec: this.varDecs){
            jott = jott + varDec.convertToJott();
        }
        jott = jott + bodyNode.convertToJott();
        return jott;
    }

    @Override
    public String convertToJava(String className) {
        String java = "";
        // if no variable declarations, should break out for loop
        for(VarDecNode varDec: this.varDecs){
            java = java + varDec.convertToJava(className);
        }
        java = java + bodyNode.convertToJava(className);
        return java;
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
    public boolean validateTree() throws SemanticErrorException{
        for(VarDecNode varDec : this.varDecs){
            varDec.validateTree();
        }

        String mustReturnType = JottParser.symTable.funcSymTab.get(funcId).get(JottParser.symTable.funcSymTab.get(funcId).size() - 1);

        // No overarching return at the bottom of the function
        if(!bodyNode.doesReturn() && mustReturnType != "Void") {
            ArrayList<BodyStmtNode> reversedArr = new ArrayList<>(bodyNode.getBodyStmts());
            Collections.reverse(reversedArr);
            boolean foundValidReturns = false;
            for(BodyStmtNode bodyStmt : reversedArr) {
                // A body statement does always return somewhere
                // Might be dead code somewhere but we don't check that
                if(bodyStmt.doesAllReturn()) {
                    foundValidReturns = true;
                    break;
                }
            }

            if(!foundValidReturns) {
                
                throw new SemanticErrorException("Method " + funcId + " must return type " + mustReturnType, null);
            }
        }
        
        this.bodyNode.validateTree();
        
        return true;
    }
    
}
