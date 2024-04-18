package nodes;

import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode{
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

    public static IfStmtNode parseIfStmt(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", null);
        }

        Token token = tokens.remove(0);

        // Check if the first token is If
        if (!token.getToken().equals("If")) {
            throw new SyntaxErrorException("Expected If, found: " + token.getToken(), token);
        }

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

        ArrayList<ElseIfNode> elseIfList = new ArrayList<>();
        token = tokens.get(0);
        // check if there is an elseif next
        while (token.getToken().equals("Elseif")) {
            // append to elseIfList
            elseIfList.add(ElseIfNode.parseElseIf(tokens, funcId));
            token = tokens.get(0);
        }
        
        // parse else
        ElseNode elseStmt = ElseNode.parseElse(tokens, funcId);
        
        return new IfStmtNode(expr, body, elseIfList, elseStmt);        
    }

    @Override
    public String convertToJott() {
        StringBuilder jottCode = new StringBuilder();
        jottCode.append("If [");
        jottCode.append(expr.convertToJott()).append("]\n{");
        jottCode.append(body.convertToJott()).append("}\n");
        for (ElseIfNode elseIfNode : elseIfList) {
                jottCode.append(elseIfNode.convertToJott());
            }
        jottCode.append(elseStmt.convertToJott());
        return jottCode.toString();
    }

    @Override
    public String convertToJava(String className) {
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("if (");
        javaCode.append(expr.convertToJava(className)).append(")\n{");
        javaCode.append(body.convertToJava(className)).append("}\n");
        for (ElseIfNode elseIfNode : elseIfList) {
                javaCode.append(elseIfNode.convertToJava(className));
            }
        javaCode.append(elseStmt.convertToJava(className));
        return javaCode.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder cCode = new StringBuilder();
        cCode.append("if (");
        cCode.append(expr.convertToC()).append(")\n{");
        cCode.append(body.convertToC()).append("}\n");
        for (ElseIfNode elseIfNode : elseIfList) {
                cCode.append(elseIfNode.convertToC());
            }
        cCode.append(elseStmt.convertToC());
        return cCode.toString();
    }

    @Override
    public String convertToPython(int tabs) {
        StringBuilder pythonCode = new StringBuilder();
        pythonCode.append("if (");
        pythonCode.append(expr.convertToPython(0)).append("):\n");
        pythonCode.append(body.convertToPython(tabs + 1)).append("\n");
        for (ElseIfNode elseIfNode : elseIfList) {
                pythonCode.append(elseIfNode.convertToPython(tabs));
            }
        pythonCode.append(elseStmt.convertToPython(tabs));
        return pythonCode.toString();
    }

    @Override
    public boolean validateTree() throws SemanticErrorException{
        this.expr.validateTree();
        this.body.validateTree();
        
        for(ElseIfNode elseif : this.elseIfList){
            elseif.validateTree();
        }
        if(this.elseStmt != null) this.elseStmt.validateTree();
        
        return true;
    }

    @Override
    public boolean doesAllReturn() {
        // check if body within if() doesn't have a return
        if(!body.doesReturn()) return false;

        // check bodies of else ifs 
        for(ElseIfNode elseIfNode : elseIfList) {
            if(!elseIfNode.doesAllReturn()) return false;
        }

        // check if else statement has also returns
        if(!elseStmt.doesAllReturn()) return false;

        return true;
        
    }
    
}
