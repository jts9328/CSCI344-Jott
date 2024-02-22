package nodes;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public interface BodyStmtNode extends JottTree{

    public static BodyStmtNode parseBodyStmtNode(ArrayList<Token> tokens) throws SyntaxErrorException {
        if (tokens == null || tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token token = tokens.get(0);
        if(token.getTokenType() == TokenType.ID_KEYWORD && token.getToken() == "If"){
            return IfStmtNode.parseIfStmtNode(tokens);
        } else if(token.getTokenType() == TokenType.ID_KEYWORD && token.getToken() == "While"){
            return WhileLoopNode.parseWhileLoopNode(tokens);
        } else if(token.getTokenType() == TokenType.ID_KEYWORD){
            return AsmtNode.parseAsmtNode(tokens);
        } else if(token.getTokenType() == TokenType.COLON){
            return FunctionCallNode.parseFunctionCallNode(tokens);
        } else{
            throw new SyntaxErrorException("Expected Body Statement", token);
        }
    }

    @Override
    public String convertToJott();

    @Override
    public String convertToJava(String className);

    @Override
    public String convertToC();

    @Override
    public String convertToPython();

    @Override
    public boolean validateTree();
    
}
