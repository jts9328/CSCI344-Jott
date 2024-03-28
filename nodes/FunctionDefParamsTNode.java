package nodes;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionDefParamsTNode implements JottTree{

    private IdNode idNode;
    private TypeNode typeNode;

    public FunctionDefParamsTNode(IdNode idNode, TypeNode typeNode){
        this.idNode = idNode;
        this.typeNode = typeNode;
    }

    public static FunctionDefParamsTNode parseFunctionDefParamsTNode(ArrayList<Token> tokens, String idString) throws SyntaxErrorException {
        // errors if EOF
        if (tokens == null || tokens.isEmpty()){
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }
        Token token = tokens.remove(0);
        // check for the leading comma
        if (token.getTokenType() != TokenType.COMMA){
            throw new SyntaxErrorException("Expected ','", token);
        }
        token = tokens.get(0);
        // parse ID
        IdNode idNode = IdNode.parseId(tokens);
        token = tokens.remove(0);
        // check for colon
        if(token.getTokenType() != TokenType.COLON){
            throw new SyntaxErrorException("Expected ':'", token);
        }
        token = tokens.get(0);
        // parse Type
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        JottParser.symTable.funcSymTab.get(idString).add(typeNode.toString());

        return new FunctionDefParamsTNode(idNode, typeNode);
    }

    @Override
    public String convertToJott() {
        return "," + idNode.convertToJott() + ": " + typeNode.convertToJott();
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
