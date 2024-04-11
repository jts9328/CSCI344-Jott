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

    public String[] getFirstTwoParamsJava(String className) {
        String val1 = exprNode.convertToJava(className);
        String val2 = paramsTNodes.get(0).getParamExprJava(className);
        String[] data = {val1, val2};
        return data;
    }

    public String getFirstParamJava(String className) {
        return exprNode.convertToJava(className);
    }

    public String[] getFirstTwoParamsPython() {
        String val1 = exprNode.convertToPython();
        String val2 = paramsTNodes.get(0).getParamExprPython();
        String[] data = {val1, val2};
        return data;
    }

    public String getFirstParamPython() {
        return exprNode.convertToPython();
    }

    public String[] getFirstTwoParamsC() {
        String val1 = exprNode.convertToC();
        String val2 = paramsTNodes.get(0).getParamExprC();
        String[] data = {val1, val2};
        return data;
    }

    public String getFirstParamC() {
        return exprNode.convertToC();
    }

    public ExprNode getExpr() {
        return exprNode;
    }


    @Override
    public String convertToC() {
        if(exprNode == null && paramsTNodes == null) {
            return "";
        } else {
            StringBuilder javaCode = new StringBuilder(exprNode.convertToC());
            for (ParamsTNode paramsTNode : paramsTNodes) {
                javaCode.append(paramsTNode.convertToC());
            }
            return javaCode.toString();
        }
    }

    @Override
    public String convertToPython(int tabs) {
        if(exprNode == null && paramsTNodes == null) {
            return "";
        } else {
            StringBuilder pythonCode = new StringBuilder(exprNode.convertToPython(tabs));
            for (ParamsTNode paramsTNode : paramsTNodes) {
                pythonCode.append(paramsTNode.convertToPython(tabs));
            }
            return pythonCode.toString();
        }
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
