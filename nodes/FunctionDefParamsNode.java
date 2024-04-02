package nodes;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionDefParamsNode implements JottTree{

    private IdNode idNode;
    private TypeNode typeNode;
    private ArrayList<FunctionDefParamsTNode> functionDefParamsTNodes;
    private String funcId;

    /**
     * Grammar: < func_def_params > --> < id >: < type > < function_def_params_t >*
     * 
     * @param exprNode      child node ExprNode 
     * @param paramsTNodes  arraylist of child nodes paramsTNode
     */
    public FunctionDefParamsNode(IdNode idNode, TypeNode typeNode, ArrayList<FunctionDefParamsTNode> functionDefParamsTNodes, String funcId) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.functionDefParamsTNodes = functionDefParamsTNodes;
        this.funcId = funcId;
    }

    /**
     * Grammar: < func_def_params > --> ε
     */
    public FunctionDefParamsNode() {
        this(null, null, null, null);
    }

    /**
     * Parses a params node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      FunctionDefParamsNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static FunctionDefParamsNode parseFunctionDefParamsNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
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

        //JottParser.symTable.funcSymTab.get(idString).add(typeNode.toString());

        // Look for <function_def_params_t>*
        ArrayList<FunctionDefParamsTNode> functionDefParamsTNodes = new ArrayList<>();

        while(tokens.get(0).getTokenType() == TokenType.COMMA) {
            functionDefParamsTNodes.add(FunctionDefParamsTNode.parseFunctionDefParamsTNode(tokens, funcId));
        }

        return new FunctionDefParamsNode(idNode, typeNode, functionDefParamsTNodes, funcId);
    }

    @Override
    public String convertToJott() {
        if(idNode == null && typeNode == null && functionDefParamsTNodes == null && funcId == null) {
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

        // No params
        if(idNode == null && typeNode == null && functionDefParamsTNodes == null && funcId == null) return true;

        // Add first param type to symbol table
        JottParser.symTable.funcSymTab.get(funcId).add(typeNode.toString());

        // Add the variable param to the var sym table
        JottParser.symTable.varSymTab.put(idNode.toString(), typeNode.toString());

        // Run subsequent params validate trees
        for (FunctionDefParamsTNode functionDefParamsTNode : functionDefParamsTNodes) {
            functionDefParamsTNode.validateTree();
        }

        return true;

        // // Validate idNode and typeNode if they are not null
        // boolean isValid = (idNode == null || idNode.validateTree()) && (typeNode == null || typeNode.validateTree());
    
        // // If functionDefParamsTNodes is not null, iterate through the list and validate each node
        // if (functionDefParamsTNodes != null) {
        //     for (FunctionDefParamsTNode functionDefParamsTNode : functionDefParamsTNodes) {
        //         isValid &= functionDefParamsTNode.validateTree();
        //     }
        // }
    
        // return isValid;
    }
    
    
}
