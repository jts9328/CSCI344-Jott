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

    public ReturnStmtNode(ExprNode expr){
        this.expr = expr;
    }

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) throws SyntaxErrorException {
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
            ReturnStmtNode returnNode = new ReturnStmtNode(ExprNode.parseExprNode(tokens));
            // remove the ';' token
            tokens.remove(0);
            // return the created node
            return returnNode;
        // no return check (valid)
        } else if(token.getTokenType() == TokenType.R_BRACKET){
            return new ReturnStmtNode(null);
        // Invalid
        } else{
            throw new SyntaxErrorException("Expected return or end of function", token);
        }
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
        this.expr.validateTree();
        // Check if return type matches func return type

        return true;
    }
    
}
