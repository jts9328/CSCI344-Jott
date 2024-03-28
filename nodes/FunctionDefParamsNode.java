package nodes;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionDefParamsNode implements JottTree{

    private IdNode idNode;
    private TypeNode typeNode;
    private ArrayList<FunctionDefParamsTNode> functionDefParamsTNodes;

    /**
     * Grammar: < func_def_params > --> < id >: < type > < function_def_params_t >*
     * 
     * @param exprNode      child node ExprNode 
     * @param paramsTNodes  arraylist of child nodes paramsTNode
     */
    public FunctionDefParamsNode(IdNode idNode, TypeNode typeNode, ArrayList<FunctionDefParamsTNode> functionDefParamsTNodes) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.functionDefParamsTNodes = functionDefParamsTNodes;
    }

    /**
     * Grammar: < func_def_params > --> ε
     */
    public FunctionDefParamsNode() {
        this(null, null, null);
    }

    /**
     * Parses a params node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      FunctionDefParamsNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static FunctionDefParamsNode parseFunctionDefParamsNode(ArrayList<Token> tokens, String idString) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        // If the next token is just a ], that means there are no params (ε)
        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            //hasParams = false;
            return new FunctionDefParamsNode();
        }

        // Look for <id>
        IdNode idNode = IdNode.parseId(tokens);

        // Look for :
        Token colonToken = tokens.remove(0);
        if(colonToken.getTokenType() != TokenType.COLON) {
            throw new SyntaxErrorException("Expected ':' but got " + colonToken.getToken(), colonToken);
        }

        // Look for <type>
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);

        // Look for <function_def_params_t>*
        ArrayList<FunctionDefParamsTNode> functionDefParamsTNodes = new ArrayList<>();

        while(tokens.get(0).getTokenType() == TokenType.COMMA) {
            functionDefParamsTNodes.add(FunctionDefParamsTNode.parseFunctionDefParamsTNode(tokens));
        }

        return new FunctionDefParamsNode(idNode, typeNode, functionDefParamsTNodes);
    }

    @Override
    public String convertToJott() {
        if(idNode == null && typeNode == null && functionDefParamsTNodes == null) {
            return "";
        } else {
            StringBuilder jottCode = new StringBuilder(idNode.convertToJott() + ":" + typeNode.convertToJott());
            for (FunctionDefParamsTNode functionDefparamsTNode : functionDefParamsTNodes) {
                jottCode.append(functionDefparamsTNode.convertToJott());
            }
            return jottCode.toString();
        }
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
