package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

public class ElseIfNode implements JottTree{
    private ExprNode expr;
    private BodyNode body;

    // ElseIf Statement Constructor
    public ElseIfNode(ExprNode expr, BodyNode body) {
        this.expr = expr;
        this.body = body;
    }

    public static ElseIfNode parseElseIf(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", null);
        }

        Token token = tokens.remove(0);
        if (token.getToken().equals("Elseif")) {
            //check for left bracket
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.L_BRACKET) {
                throw new SyntaxErrorException("Expected L_BRACKET, found: " + token.getTokenType(), token);
            }
            //parse expression
            ExprNode expr = ExprNode.parseExprNode(tokens);
            //check for right bracket
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.R_BRACKET) {
                throw new SyntaxErrorException("Expected R_BRACKET, found: " + token.getTokenType(), token);
            }

            //check for left brace
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.L_BRACE) {
                throw new SyntaxErrorException("Expected L_BRACE, found: " + token.getTokenType(), token);
            }
            //parse body
            BodyNode body = BodyNode.parseBodyNode(tokens, funcId);
            //check for right brace
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.R_BRACE) {
                throw new SyntaxErrorException("Expected R_BRACE, found: " + token.getTokenType(), token);
            }

            return new ElseIfNode(expr, body);
        } else {
            throw new SyntaxErrorException("Expected Elseif, found: " + token.getToken(), token);
        }
    }

    // public String getReturnType() throws SemanticErrorException{
        // return this.body.getReturnType();
    // }

    @Override
    public String convertToJott() {
        StringBuilder jottCode = new StringBuilder();
        jottCode.append("Elseif [");
        jottCode.append(expr.convertToJott()).append("]\n{");
        jottCode.append(body.convertToJott()).append("}\n");
        return jottCode.toString();
    }

    @Override
    public String convertToJava(String className) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("else if (");
        javaCode.append(expr.convertToJava(className)).append(")\n{");
        javaCode.append(body.convertToJava(className)).append("}\n");
        return javaCode.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder cCode = new StringBuilder();
        cCode.append("else if (");
        cCode.append(expr.convertToC()).append(")\n{");
        cCode.append(body.convertToC()).append("}\n");
        return cCode.toString();
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {
        this.expr.validateTree();
        this.body.validateTree();
        return true;
    }

    public boolean doesAllReturn() {
        return body.doesReturn();
    }
    
}
