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
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("while (");
        javaCode.append(expr.convertToJava(className)).append(")\n{");
        javaCode.append(body.convertToJava(className)).append("}\n");
        return javaCode.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder cCode = new StringBuilder();
        cCode.append("while (");
        cCode.append(expr.convertToC()).append(")\n{");
        cCode.append(body.convertToC()).append("}\n");
        return cCode.toString();
    }

    @Override
    public String convertToPython(int tabs) {
        StringBuilder pythonCode = new StringBuilder();
        for(int i = 0; i < tabs; i++){
            pythonCode = pythonCode.append("\t");
        }
        pythonCode.append("while (");
        pythonCode.append(expr.convertToPython(0)).append("): \n");
        pythonCode.append(body.convertToPython(tabs + 1)).append("\n");
        return pythonCode.toString();
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {
        this.expr.validateTree();
        this.body.validateTree();

        return true;
    }

    @Override
    public boolean doesAllReturn() {
        // False because there is always the case the loop is not entered
        // If there is a return afterwards, it will have already been caught by the 
        // loop in fbodynode's validateTree()
        return false;
    }

}
