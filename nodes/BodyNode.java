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
    private String funcId;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmts, ReturnStmtNode returnStmt, String funcId){
        this.bodyStmts = bodyStmts;
        this.returnStmt = returnStmt;
        this.funcId = funcId;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        // EOF
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        ArrayList<BodyStmtNode> bodyStmts = new ArrayList<BodyStmtNode>();

        // While next token is not return or ] (end of function) and token is either an id (If, While, <id> from asmt) or fc header (::)
        while(tokens.isEmpty()==false && (!(tokens.get(0).getToken().equals("Return") || tokens.get(0).getTokenType() == TokenType.R_BRACE) && 
               (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD || tokens.get(0).getTokenType() == TokenType.FC_HEADER))) {
            bodyStmts.add(BodyStmtNode.parseBodyStmtNode(tokens, funcId));
        }

        // Should be Return or }

        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        
        //Token endingToken = tokens.get(0);

        ReturnStmtNode returnStmt = ReturnStmtNode.parseReturnStmtNode(tokens, funcId);

        return new BodyNode(bodyStmts, returnStmt, funcId);

        // if(endingToken.getTokenType() == TokenType.ID_KEYWORD && endingToken.getToken().equals("Return")){
        //     ReturnStmtNode returnStmt = ReturnStmtNode.parseReturnStmtNode(tokens);
        //     return new BodyNode(bodyStmts, returnStmt);
        // } else if(endingToken.getTokenType() == TokenType.R_BRACE){
        //     return new BodyNode(bodyStmts, null);
        // } else{
        //     throw new SyntaxErrorException("Expected Return statement or '}' but got " + endingToken.getToken(), endingToken);
        // }
    }

    public String getReturnType() throws SemanticErrorException {

        return returnStmt.getReturnType();
        // String returnStmtType = "";
        // if(this.returnStmt != null){
        //     returnStmtType = this.returnStmt.getReturnType();
        // }

        // ArrayList<String> bodyStmtsReturns = new ArrayList<String>();
        // for(BodyStmtNode bodyStmt: bodyStmts){
        //     bodyStmtsReturns.add(bodyStmt.getReturnType());
        // }

        // String returnPrime = bodyStmtsReturns.get(0);
        // for(String bodyStmtReturn: bodyStmtsReturns){
        //     if(!(returnPrime.equals(bodyStmtReturn))){
        //         throw new SemanticErrorException("1Return types of body statements do not match", getReturnToken());
        //     }
        // }

        // if(!(returnStmtType.equals(returnPrime))){
        //     if(returnPrime.equals("Void")){
        //         return returnStmtType;
        //     } else if(returnStmtType.equals("Void")){
        //         return returnPrime;
        //     } else{
        //         throw new SemanticErrorException("2Return types of body statements do not match", getReturnToken());
        //     }
        // }
        // if(this.returnStmt != null){
        //     return returnStmtType;
        // } else{
        //     return returnPrime;
        // }
    }

    public Token getReturnToken() {
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
    public boolean validateTree() throws SemanticErrorException{
        for(BodyStmtNode bodyStmt : this.bodyStmts){
            bodyStmt.validateTree();
        }
        if(this.returnStmt != null){
            this.returnStmt.validateTree();
        }
        return true;
    }
    
}
