package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ReturnStmtNode implements JottTree{
    
    private ExprNode expr;
    private String funcId;
    private boolean mustReturn;

    public ReturnStmtNode(ExprNode expr, String funcId){
        this.expr = expr;
        this.funcId = funcId;
        this.mustReturn = mustReturn;
    }

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        // EOF
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        } 
        Token token = tokens.get(0);
        // Return check
        if(token.getTokenType() == TokenType.ID_KEYWORD && token.getToken().equals("Return")){
            // remove 'Return' token
            tokens.remove(0);
            // parse the expression (should remove expression token)
            ReturnStmtNode returnNode = new ReturnStmtNode(ExprNode.parseExprNode(tokens), funcId);
            // remove the ';' token
            tokens.remove(0);
            // return the created node
            return returnNode;
        // no return check (valid)
        } else if(token.getTokenType() == TokenType.R_BRACE){
            return new ReturnStmtNode(null, funcId);
        // Invalid
        } else{
            throw new SyntaxErrorException("Expected Return statement or '}' but got " + token.getToken(), token);
        }
    }

    // public String getReturnType(){
    //     if(expr != null) {
    //         return this.expr.getReturnType();
    //     }
        
    //     return "Void";
    // }

    public Token getToken(){
        if(expr != null) {
            return this.expr.getToken();
        }
        
        return null;
    }

    @Override
    public String convertToJott() {
        // No return check
        if(this.expr == null){
            return "";
        // return check
        } else{
            return "Return " + this.expr.convertToJott() + ";";
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
    public boolean validateTree() throws SemanticErrorException{
        if(expr != null) {
            expr.validateTree();

            ArrayList<String> funcTypes = JottParser.symTable.funcSymTab.get(funcId);
            if(expr.getReturnType().equals(funcTypes.get(funcTypes.size()-1))){
                return true;
            } else {
                throw new SemanticErrorException("Return type does match function definition", getToken());
            }
        } else {
            return true;
        }
        
    }

    public boolean doesReturn() {
        return expr != null;
    }

}
