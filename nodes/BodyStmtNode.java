package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public interface BodyStmtNode extends JottTree{

    public static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        // If EOF
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token token = tokens.get(0);
        // If check
        if(token.getTokenType() == TokenType.ID_KEYWORD && token.getToken().equals("If")){
            return IfStmtNode.parseIfStmt(tokens, funcId);
        // While check
        } else if(token.getTokenType() == TokenType.ID_KEYWORD && token.getToken().equals("While")){
            return WhileLoopNode.parseWhileLoop(tokens, funcId);
        // Assignment check
        } else if(token.getTokenType() == TokenType.ID_KEYWORD){
            return AsmtNode.parseAsmtNode(tokens, funcId);
        // Function call check
        } else if(token.getTokenType() == TokenType.FC_HEADER){
            FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
            token = tokens.remove(0); // remove ;
            if (token.getTokenType() != TokenType.SEMICOLON) {
                throw new SyntaxErrorException("Expected Semicolon, but got " + token.getTokenType(), token);
            }
            return functionCallNode;
        // Invalid
        } else{
            throw new SyntaxErrorException("Expected Body Statement, but got " + token.getToken(), token);
        }
    }

    // public String getReturnType() throws SemanticErrorException;

    @Override
    public String convertToJott();

    @Override
    public String convertToJava(String className);

    @Override
    public String convertToC();

    @Override
    public String convertToPython();

    @Override
    public boolean validateTree() throws SemanticErrorException;

    public boolean validateTree(String funcId) throws SemanticErrorException;
    
}
