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

    public static ElseIfNode parseElseIf(ArrayList<Token> tokens) throws SyntaxErrorException {
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
            BodyNode body = BodyNode.parseBodyNode(tokens);
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
