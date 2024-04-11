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
    private String funcId;

    public FunctionDefParamsTNode(IdNode idNode, TypeNode typeNode, String funcId){
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.funcId = funcId;
    }

    public static FunctionDefParamsTNode parseFunctionDefParamsTNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        // errors if EOF
        if (tokens == null || tokens.isEmpty()){
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        Token token = tokens.remove(0);
        // check for the leading comma
        if (token.getTokenType() != TokenType.COMMA){
            throw new SyntaxErrorException("Expected ','", token);
        }


        // parse ID
        IdNode idNode = IdNode.parseId(tokens);

        token = tokens.remove(0);
        // check for colon
        if(token.getTokenType() != TokenType.COLON){
            throw new SyntaxErrorException("Expected ':'", token);
        }
        
        // parse Type
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        return new FunctionDefParamsTNode(idNode, typeNode, funcId);
    }

    @Override
    public String convertToJott() {
        return "," + idNode.convertToJott() + ": " + typeNode.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return "," + typeNode.convertToJava(className) + idNode.convertToJava(className);
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
        // Add param to symbol table
        JottParser.symTable.funcSymTab.get(funcId).add(typeNode.toString());
        return true;
    }
    
}
