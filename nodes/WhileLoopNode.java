package nodes;

import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

public class WhileLoopNode implements BodyStmtNode{
    private ExprNode expr;
    private BodyNode body;

    // WhileLoopNode Statement Constructor
    public WhileLoopNode(ExprNode expr, BodyNode body) {
        this.expr = expr;
        this.body = body;
    }

    public static WhileLoopNode parseWhileLoop(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", null);
        }

        Token token = tokens.remove(0);
        if (token.getToken().equals("While")) {
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

            return new WhileLoopNode(expr, body);
        } else {
            throw new SyntaxErrorException("Expected While, found: " + token.getToken(), token);
        }
    }

    public String getReturnType() throws SemanticErrorException{
        return this.body.getReturnType();
    }

    @Override
    public String convertToJott() {
        StringBuilder jottCode = new StringBuilder();
        jottCode.append("While [");
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
    public boolean validateTree() throws SemanticErrorException {
        this.expr.validateTree();
        this.body.validateTree();

        return true;
    }

}
