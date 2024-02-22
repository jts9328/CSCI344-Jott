package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

import exceptions.SyntaxErrorException;

public class VarDecNode implements JottTree{
    private TypeNode type;
    private IdNode id;

    /**
     *  Grammar: <Var_Dec> -> < type > < id>;
     * 
     * @param type  child node that is a type
     * @param id child node that is an id
     */
    public VarDecNode(TypeNode type, IdNode id){
        this.type = type;
        this.id = id;
    }

    /**
     * parses a Variable Declaration Node given the list of remaining tokens
     * 
     * @param tokens                    arraylist of tokens
     * @return                          Variable Declaration childrens returns
     * @throws SyntaxErrorException     one of Child Nodes was incorrect
     */
    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws SyntaxErrorException{
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        IdNode idNode = IdNode.parseId(tokens);
        return new VarDecNode(typeNode, idNode);        
    }

    @Override
    public String convertToJott() {
        return type.convertToJott() + id.convertToJott();
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
