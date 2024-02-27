package nodes;

import provided.JottTree;
import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

public class IfStmtNode implements JottTree{
    private ExprNode expr;
    private BodyNode body;
    private ArrayList<ElseIfNode> elseIfList;
    private ElseNode else;

    // If Statement Constructor
    public IfStmtNode(ExprNode expr, BodyNode body, ArrayList<ElseIfNode> elseIfList, ElseNode else) {
        this.expr = expr;
        this.body = body;
        this.elseIfList = elseIfList;
        this.else = else;
    }

    public static IfStmtNode parseIfStmt(ArrayList<Token> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            // TODO Throw exception instead of NULL
            return null; // No tokens to parse
        }

        Token token = tokens.remove(0);

        // Check if the first token is If
        if (token.getToken().equals("If")) {
            //Throw error if 'if' keyword is not found
            System.err.println("Expected If, found: " + token.getToken());
            // TODO Throw exception instead of NULL
            return null;
        }

        //check for left bracket
        token = token.remove(0);
        if (token.getTokenType() != TokenType.L_BRACKET) {
            System.err.println("Expected L_BRACKET, found: " + token.getTokenType())
            // TODO Throw exception instead of NULL
            return null;
        }
        //parse expression
        ExprNode expr = ExprNode.parseExpr(tokens);
        //check for right bracket
        token = token.remove(0);
        if (token.getTokenType() != TokenType.R_BRACKET) {
            System.err.println("Expected R_BRACKET, found: " + token.getTokenType())
            // TODO Throw exception instead of NULL
            return null;
        }

        //check for left brace
        token = token.remove(0);
        if (token.getTokenType() != TokenType.L_BRACE) {
            System.err.println("Expected L_BRACE, found: " + token.getTokenType())
            // TODO Throw exception instead of NULL
            return null;
        }
        //parse body
        BodyNode body = BodyNode.parseBody(tokens);
        //check for right brace
        token = token.remove(0);
        if (token.getTokenType() != TokenType.R_BRACE) {
            System.err.println("Expected R_BRACE, found: " + token.getTokenType())
            // TODO Throw exception instead of NULL
            return null;
        }

        ArrayList<ElseIfNode> elseIfList;
        token = tokens.get(0);
        //check if there is an elseif next
        while (token.getToken().equals("Elseif")) {
            //apend to elseIfList
            elseIfList.append(ElseIfNode.paseElseIf(tokens));
            token = tokens.get(0);
        }

        //parse else
        ElseNode else = ElseNode.parseElse(tokens);
        
        return new IfStmtNode(expr, body, elseIfList, else);
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
