package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FBodyNode implements JottTree{

    private BodyNode bodyNode;
    private ArrayList<VarDecNode> varDecs = new ArrayList<VarDecNode>();

    public FBodyNode(BodyNode bodyNode, ArrayList<VarDecNode> varDecs){
        this.bodyNode = bodyNode;
        this.varDecs = varDecs;
    }

    public static FBodyNode parseFBodyNode(ArrayList<Token> tokens) throws SyntaxErrorException {
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
            varDecs.add(VarDecNode.parseVarDecNode(tokens));
            token = tokens.get(0);
        }
        // checks and exception handling should be caught by parseBodyNode
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
        return new FBodyNode(bodyNode, varDecs);
    }

    public String getReturnType() throws SemanticErrorException{
        return this.bodyNode.getReturnType();
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
    public boolean validateTree() throws SemanticErrorException{
        for(VarDecNode varDec : this.varDecs){
            varDec.validateTree();
        }
        if(this.bodyNode != null){
            this.bodyNode.validateTree();
        }
        return true;
    }
    
}
