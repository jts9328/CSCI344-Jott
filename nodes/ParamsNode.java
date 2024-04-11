package nodes;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;
import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ParamsNode implements JottTree{

    private ExprNode exprNode;
    private ArrayList<ParamsTNode> paramsTNodes;
    private String funcId;
    private Token rBracketToken;

    /**
     * Grammar: < params > --> < expr > < params_t >*
     * 
     * @param exprNode      child node ExprNode 
     * @param paramsTNodes  arraylist of child nodes paramsTNode
     * @param rBracketToken token to throw an error on if necessary
     */
    public ParamsNode(ExprNode exprNode, ArrayList<ParamsTNode> paramsTNodes, Token rBracketToken, String funcId) {
        this.exprNode = exprNode;
        this.paramsTNodes = paramsTNodes;
        this.rBracketToken = rBracketToken;
        this.funcId = funcId;
    }

    /**
     * Grammar: < params > --> ε
     */
    public ParamsNode(Token rBracketToken, String funcId) {
        this(null, null, rBracketToken, funcId);
    }

    /**
     * Parses a params node given the list of remaining tokens
     * 
     * @param tokens                arraylist of tokens
     * @return                      ParamsNode complete with children
     * @throws SyntaxErrorException if a syntax error is detected
     */
    public static ParamsNode parseParamsNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        // If the next token is just a ], that means there are no params (ε)
        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            return new ParamsNode(tokens.get(0), funcId);
        }

        // Look for <expr>
        ExprNode exprNode = ExprNode.parseExprNode(tokens);

        // Look for <params_t>*
        ArrayList<ParamsTNode> paramsTNodes = new ArrayList<>();

        while(tokens.get(0).getTokenType() == TokenType.COMMA) {
            paramsTNodes.add(ParamsTNode.parseParamsTNode(tokens));
        }

        return new ParamsNode(exprNode, paramsTNodes, tokens.get(0), funcId);
    }

    @Override
    public String convertToJott() {
        if(exprNode == null && paramsTNodes == null) {
            return "";
        } else {
            StringBuilder jottCode = new StringBuilder(exprNode.convertToJott());
            for (ParamsTNode paramsTNode : paramsTNodes) {
                jottCode.append(paramsTNode.convertToJott());
            }
            return jottCode.toString();
        }
    }

    @Override
    public String convertToJava(String className) {
        if(exprNode == null && paramsTNodes == null) {
            return "";
        } else {
            StringBuilder javaCode = new StringBuilder(exprNode.convertToJava(className));
            for (ParamsTNode paramsTNode : paramsTNodes) {
                javaCode.append(paramsTNode.convertToJava(className));
            }
            return javaCode.toString();
        }
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
    public boolean validateTree() throws SemanticErrorException {
        ArrayList<String> paramTypes = new ArrayList<String>(JottParser.symTable.funcSymTab.get(funcId));

        paramTypes.remove(paramTypes.size() - 1); // Remove the return type from the list

        // No params given
        if(exprNode == null) {
            // function required params, throw error
            if(!paramTypes.isEmpty()) {
                throw new SemanticErrorException("Incorrect number of arguments for function " + funcId, rBracketToken);
            } else {
                return true;
            }
            
        }
        
        // Check if the first param is correct

        String firstType = paramTypes.remove(0);
        if(!firstType.equals("Any") && !exprNode.getReturnType().equals(firstType)) {
            throw new SemanticErrorException("Incorrect parameter type(s) for function " + funcId + " got " + exprNode.getReturnType(), exprNode.getToken());
        }

        exprNode.validateTree();

        // Check if the following params are correct
        for (ParamsTNode paramsTNode : paramsTNodes) {
            String type = paramTypes.remove(0);
            if(!type.equals("Any") && !paramsTNode.getReturnType().equals(type)) {
                throw new SemanticErrorException("Incorrect parameter type(s) for function " + funcId, exprNode.getToken());
            }

            paramsTNode.validateTree();
        }

        // Function was not given all the params
        if(!paramTypes.isEmpty()) {
            throw new SemanticErrorException("Incorrect number of arguments for function " + funcId, exprNode.getToken());
        }

        return true;
    }
    
    
}
