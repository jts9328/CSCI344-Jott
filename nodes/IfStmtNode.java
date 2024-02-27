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
    private ElseNode elseStmt;

    // If Statement Constructor
    public IfStmtNode(ExprNode expr, BodyNode body, ArrayList<ElseIfNode> elseIfList, ElseNode elseStmt) {
        this.expr = expr;
        this.body = body;
        this.elseIfList = elseIfList;
        this.elseStmt = elseStmt;
    }

    public static IfStmtNode parseIfStmt(ArrayList<Token> tokens) throws SyntaxErrorException {
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

        ArrayList<ElseIfNode> elseIfList = new ArrayList<>();
        token = tokens.get(0);
        // check if there is an elseif next
        while (token.getToken().equals("Elseif")) {
            // append to elseIfList
            elseIfList.add(ElseIfNode.parseElseIf(tokens));
            token = tokens.get(0);
        }
        
        // parse else
        ElseNode elseStmt = ElseNode.parseElse(tokens);
        
        return new IfStmtNode(expr, body, elseIfList, elseStmt);        
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
