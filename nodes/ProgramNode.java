package nodes;

import provided.JottTree;
import provided.Token;
import provided.JottParser;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
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

    public static ProgramNode parseProgram(ArrayList<Token> tokens) throws SyntaxErrorException {
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
        StringBuilder javaCode = new StringBuilder();
        javaCode.append("package " + className.split("/")[0] + ";\n");
        javaCode.append("public class " + className.split("/")[1] + "{");
        for (FunctionDefNode funcDef : functionDefs) {
            javaCode.append(funcDef.convertToJava(className));
        }
        return javaCode.toString() + "\n}";
    }

    @Override
    public String convertToC() {
        StringBuilder javaCode = new StringBuilder();
        for (FunctionDefNode funcDef : functionDefs) {
            javaCode.append(funcDef.convertToC());
        }
        return javaCode.toString();
    }

    @Override
    public String convertToPython(int tabs) {
        // StringBuilder to accumulate the Jott code representation of each function definition
        StringBuilder pythonCode = new StringBuilder();
        for (FunctionDefNode funcDef : functionDefs) {
            // Append each function definition's python code representation to the StringBuilder
            pythonCode.append(funcDef.convertToPython(tabs));
        }
        return pythonCode.toString(); // Return the complete Python code for the program
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {
        for(FunctionDefNode functionDefNode : functionDefs) {
            functionDefNode.validateTree();
        }

        if (!JottParser.symTable.funcSymTab.containsKey("main")) {
            
            throw new SemanticErrorException("Missing main function", null);
        }

        return true;
    }
    
}
