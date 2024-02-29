package nodes;

import provided.JottTree;
import provided.JottParser;
import provided.Token;
import provided.TokenType;

import exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionDefNode implements JottTree {
    private String id;
    private ArrayList<FunctionDefParamsNode> params;
    private String returnType;
    private FBodyNode fBody;

    // Constructor
    public FunctionDefNode(String id, ArrayList<FunctionDefParamsNode> params, String returnType, FBodyNode fBody) {
        this.id = id;
        this.params = params;
        this.returnType = returnType;
        this.fBody = fBody;
    }

    public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens, ArrayList<FunctionDefNode> funcDefs) throws SyntaxErrorException {
        if (tokens.isEmpty()) {
            throw new SyntaxErrorException("No tokens available for parsing.", null);
        }
    
        Token firstToken = tokens.remove(0);
        if (firstToken.getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxErrorException("Expected ID for FunctionDef in " + firstToken.getFilename() + " at " + firstToken.getLineNum() + ", found: " + firstToken.getTokenType(), firstToken);
        }
        String id = firstToken.getToken();
        String fileName = firstToken.getFilename();
        int lineNumber = firstToken.getLineNum();
    
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxErrorException("Expected [ after ID for FunctionDef in " + fileName + " at " + lineNumber, tokens.isEmpty() ? null : tokens.get(0));
        }
        tokens.remove(0); // Remove the "["
    
        ArrayList<FunctionDefParamsNode> params = new ArrayList<>();
        boolean firstParam = true;
    
        while (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            if (!firstParam) {
                if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.COMMA) {
                    throw new SyntaxErrorException("Expected , between params for FunctionDef in " + fileName + " at " + lineNumber, tokens.isEmpty() ? null : tokens.get(0));
                }
                tokens.remove(0); // Remove the comma
            } else {
                firstParam = false;
            }
    
            if (tokens.isEmpty()) {
                throw new SyntaxErrorException("Unexpected end of tokens while parsing parameters.", null);
            }
    
            params.add(FunctionDefParamsNode.parseFunctionDefParamsNode(tokens));
        }
        tokens.remove(0); // Remove the "]"
    
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.COLON) {
            throw new SyntaxErrorException("Expected : after parameters for FunctionDef in " + fileName + " at " + lineNumber, tokens.isEmpty() ? null : tokens.get(0));
        }
        tokens.remove(0); // Remove the ":"
    
        if (tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected end of tokens when expecting return type.", null);
        }
        String returnType = tokens.remove(0).getToken();
    
        if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxErrorException("Expected function body to start with { for FunctionDef in " + fileName + " at " + lineNumber, tokens.isEmpty() ? null : tokens.get(0));
        }
        tokens.remove(0); // Remove the "{"
    
        FBodyNode fBody = FBodyNode.parseFBodyNode(tokens);
    
        return new FunctionDefNode(id, params, returnType, fBody);
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
