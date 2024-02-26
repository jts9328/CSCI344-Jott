package nodes;

import java.util.ArrayList;

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
        Token token = tokens.get(0);
        ArrayList<BodyStmtNode> bodyStmts = new ArrayList<BodyStmtNode>();
        // loop until its a bracket or a return statement
        while(token.getTokenType() != TokenType.R_BRACKET || (token.getTokenType() == TokenType.ID_KEYWORD && token.getToken().equals("Return"))){
            bodyStmts.add(BodyStmtNode.parseBodyStmtNode(tokens));
        }
        if(token.getTokenType() == TokenType.ID_KEYWORD && token.getToken().equals("Return")){
            ReturnStmtNode returnStmt = ReturnStmtNode.parseReturnStmtNode(tokens);
            return new BodyNode(bodyStmts, returnStmt);
        } else if(token.getTokenType() == TokenType.R_BRACKET){
            ReturnStmtNode returnStmt = null;
            return new BodyNode(bodyStmts, returnStmt);
        } else{
            throw new SyntaxErrorException("Expected Return statement or '}'", token);
        }
    }
    @Override
    public String convertToJott() {
        String jott = "";
        // if no body statements, should break out for loop right away
        for(BodyStmtNode bodyStmt: bodyStmts){
            jott = jott + bodyStmt.convertToJott();
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
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
}
