package nodes;

import provided.JottTree;
import provided.JottParser;
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

    public static WhileLoopNode parseWhileLoop(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            // TODO Throw exception instead of NULL
            return null; // No tokens to parse
        }

        Token token = tokens.remove(0);
        if (token.getToken().equals("While")) {
            //check for left bracket
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.L_BRACKET) {
                System.err.println("Expected L_BRACKET, found: " + token.getTokenType());
                // TODO Throw exception instead of NULL
                return null;
            }
            //parse expression
            ExprNode expr = ExprNode.parseExprNode(tokens);
            //check for right bracket
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.R_BRACKET) {
                System.err.println("Expected R_BRACKET, found: " + token.getTokenType());
                // TODO Throw exception instead of NULL
                return null;
            }

            //check for left brace
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.L_BRACE) {
                System.err.println("Expected L_BRACE, found: " + token.getTokenType());
                // TODO Throw exception instead of NULL
                return null;
            }
            //parse body
            BodyNode body = BodyNode.parseBodyNode(tokens);
            //check for right brace
            token = tokens.remove(0);
            if (token.getTokenType() != TokenType.R_BRACE) {
                System.err.println("Expected R_BRACE, found: " + token.getTokenType());
                // TODO Throw exception instead of NULL
                return null;
            }

            return new WhileLoopNode(expr, body);
        } else {
            System.err.println("Expected While, found: " + token.getToken());
            // TODO Throw exception instead of NULL
            return null;
        }
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
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
