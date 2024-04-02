package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class BodyNode implements JottTree{
    // For Kleene star, will need to parse the body stmt 
    // in a while loop until it isnt a body stmt anymore 
    // and then parse the return, which can also be nothing
    // So remember that kleene star can also be nothing

    private ArrayList<BodyStmtNode> bodyStmts;
    private ReturnStmtNode returnStmt;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmts, ReturnStmtNode returnStmt){
        this.bodyStmts = bodyStmts;
        this.returnStmt = returnStmt;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws SyntaxErrorException {
        // EOF
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        ArrayList<BodyStmtNode> bodyStmts = new ArrayList<BodyStmtNode>();

        // While next token is not return or ] (end of function) and token is either an id (If, While, <id> from asmt) or fc header (::)
        while(tokens.isEmpty()==false && (!(tokens.get(0).getToken().equals("Return") || tokens.get(0).getTokenType() == TokenType.R_BRACE) && 
               (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD || tokens.get(0).getTokenType() == TokenType.FC_HEADER))) {
            bodyStmts.add(BodyStmtNode.parseBodyStmtNode(tokens));
        }

        // Should be Return or }

        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token endingToken = tokens.get(0);

        if(endingToken.getTokenType() == TokenType.ID_KEYWORD && endingToken.getToken().equals("Return")){
            ReturnStmtNode returnStmt = ReturnStmtNode.parseReturnStmtNode(tokens);
            return new BodyNode(bodyStmts, returnStmt);
        } else if(endingToken.getTokenType() == TokenType.R_BRACE){
            ReturnStmtNode returnStmt = null;
            return new BodyNode(bodyStmts, returnStmt);
        } else{
            throw new SyntaxErrorException("Expected Return statement or '}' but got " + endingToken.getToken(), endingToken);
        }
    }

    public String getReturnType(){
        return this.returnStmt.getReturnType();
    }

    public Token getReturnToken(){
        return this.returnStmt.getToken();
    }

    @Override
    public String convertToJott() {
        String jott = "";
        // if no body statements, should break out for loop right away
        for(BodyStmtNode bodyStmt: bodyStmts){
            jott = jott + bodyStmt.convertToJott();
            if(bodyStmt instanceof FunctionCallNode){
                jott = jott + ";";
            }
        }
        if(returnStmt != null){
            jott = jott + returnStmt.convertToJott();
        }
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
    public boolean validateTree() throws SemanticErrorException {
        for(BodyStmtNode bodyStmt : this.bodyStmts){
            bodyStmt.validateTree();
        }
        if(this.returnStmt != null){
            this.returnStmt.validateTree();
        }
        return true;
    }
    
}
