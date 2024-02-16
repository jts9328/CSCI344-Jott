package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class FunctionDefNode implements JottTree {
    private String id;
    private FunctionDefParamsNode params;
    private FunctionReturnNode returnType;
    private FBodyNode fBody;

    // Constructor
    public FunctionDefNode(String id, FunctionDefParamsNode params, FunctionReturnNode returnType, FBodyNode fBody) {
        this.id = id;
        this.params = params;
        this.returnType = returnType;
        this.fBody = fBody;
    }

    public static FunctionDefNode ParseFuncDef(ArrayList<Token> tokens){
        
        return null;
    }

    @Override
    public String convertToJott() {
        StringBuilder jottCode = new StringBuilder();
        jottCode.append("Def ").append(id).append("[");
        if (params != null) {
            jottCode.append(params.convertToJott());
        }
        jottCode.append("]: ").append(returnType.convertToJott()).append("{\n");
        jottCode.append(fBody.convertToJott());
        jottCode.append("}\n");
        return jottCode.toString();
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
