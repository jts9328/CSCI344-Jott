package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.SyntaxErrorException;


public class ProgramNode implements JottTree {
    // An ArrayList to hold zero or more FunctionDefNodes
    private final ArrayList<FunctionDefNode> functionDefs;

    // Constructor that initializes the ProgramNode with an optional list of function definitions
    public ProgramNode(ArrayList<FunctionDefNode> functionDefs) {
        if (functionDefs == null) {
            this.functionDefs = new ArrayList<>(); // Ensure list is initialized even if no functions are defined
        } else {
            this.functionDefs = functionDefs;
        }
    }

    public static ProgramNode parseProgram(ArrayList<Token> tokens, HashMap<String, String> varSymTab, HashMap<String, ArrayList<String>> funcSymTab) throws SyntaxErrorException {
        ArrayList<FunctionDefNode> functionDefs = new ArrayList<>();
        while (!tokens.isEmpty()) {
            // create new funciton def
            functionDefs.add(FunctionDefNode.parseFunctionDefNode(tokens));
        }
        return new ProgramNode(functionDefs);
    }

    @Override
    public String convertToJott() {
        // StringBuilder to accumulate the Jott code representation of each function definition
        StringBuilder jottCode = new StringBuilder();
        for (FunctionDefNode funcDef : functionDefs) {
            // Append each function definition's Jott code representation to the StringBuilder
            jottCode.append(funcDef.convertToJott());
        }
        return jottCode.toString(); // Return the complete Jott code for the program
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
