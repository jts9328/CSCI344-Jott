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
    public FunctionDefNode(IdNode idNode, FunctionDefParamsNode functionDefParamsNode,
            FunctionReturnNode functionReturnNode, FBodyNode fBodyNode) {
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
        if (lbToken.getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxErrorException("Expected left square bracket but got " + lbToken.getToken(), lbToken);
        }

        FunctionDefParamsNode functionDefParamsNode = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens,
                idString);

        // Look for ]
        Token rbToken = tokens.remove(0);
        if (rbToken.getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxErrorException("Expected right square bracket but got " + rbToken.getToken(), rbToken);
        }

        // Look for :
        Token colonToken = tokens.remove(0);
        if (colonToken.getTokenType() != TokenType.COLON) {
            throw new SyntaxErrorException("Expected colon but got " + colonToken.getToken(), colonToken);
        }

        FunctionReturnNode functionReturnNode = FunctionReturnNode.parseFunctionReturnNode(tokens, idString);

        // Look for {
        Token lbraceToken = tokens.remove(0);
        if (lbraceToken.getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxErrorException("Expected left brace but got " + lbraceToken.getToken(), lbraceToken);
        }

        FBodyNode fBodyNode = FBodyNode.parseFBodyNode(tokens, idString);

        // Look for }
        Token rbraceToken = tokens.remove(0);
        if (rbraceToken.getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxErrorException("Expected right brace but got " + rbraceToken.getToken(), rbraceToken);
        }

        return new FunctionDefNode(idNode, functionDefParamsNode, functionReturnNode, fBodyNode);
    }

    @Override
    public String convertToJott() {
        return "Def " + idNode.convertToJott() + "[" + functionDefParamsNode.convertToJott() + "]:"
                + functionReturnNode.convertToJott() + "{" + fBodyNode.convertToJott() + "}";
    }

    @Override
    public String convertToJava(String className){
        String j_return;

        String main = "";
        if ( this.idNode.getToken().getToken().equals("main")) {
            main = "String[] args";
        }
        if(this.functionDefParamsNode == null){
            j_return = "public static " + this.functionReturnNode.convertToJava(className) + " " + this.idNode.convertToJava(className)
                    + "(" + main + "){\n" + this.fBodyNode.convertToJava(className) + "}\n";
        }
        else{
            j_return = "public static " + this.functionReturnNode.convertToJava(className) + " " + this.idNode.convertToJava(className)
                    + "(" + this.functionDefParamsNode.convertToJava(className) + "){\n" + this.fBodyNode.convertToJava(className) + "}\n";
        }
        return j_return;
    }

    @Override
    public String convertToC() {
        String c_return;
        if ( this.idNode.getToken().getToken().equals("main")) {
            c_return = "int main(void){\n" + this.fBodyNode.convertToC();
            c_return += "return 1;\n";
            c_return += "}\n";
        }
        else if(this.functionDefParamsNode == null){
            c_return = this.functionReturnNode.convertToC() + " " + this.idNode.convertToC()
                    + "(void){\n" + this.fBodyNode.convertToC() + "}\n";
        }
        else{
            c_return = this.functionReturnNode.convertToC() + " " + this.idNode.convertToC()
                    + "(" + this.functionDefParamsNode.convertToC() + "){\n" + this.fBodyNode.convertToC() + "}\n";
        }
        return c_return;
    }

    @Override
    public String convertToPython(int tabs) {
        return "def " + this.idNode.convertToPython(0) + "(" + 
                            this.functionDefParamsNode.convertToPython(0) +
                            "):\n" + this.fBodyNode.convertToPython(tabs);
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {

        // If the func id already exists, throw a semantic error

        if (JottParser.symTable.funcSymTab.containsKey(idNode.toString())) {
            throw new SemanticErrorException("Duplicate function " + idNode.toString(), idNode.getToken());
        }

        JottParser.symTable.funcSymTab.put(idNode.toString(), new ArrayList<>());

        functionDefParamsNode.validateTree();

        functionReturnNode.validateTree();

        if (idNode.toString().equals("main") && !functionReturnNode.getReturnType().equals("Void")) {
            throw new SemanticErrorException("Main must be of return Void", idNode.getToken());
        }

        fBodyNode.validateTree();

        return true;
    }

}
