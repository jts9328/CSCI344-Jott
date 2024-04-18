package nodes;

import provided.JottParser;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;

public class VarDecNode implements JottTree{
    private TypeNode type;
    private IdNode id;
    private String funcId;

    /**
     *  Grammar: <Var_Dec> -> < type > < id>;
     * 
     * @param type  child node that is a type
     * @param id child node that is an id
     */
    public VarDecNode(TypeNode type, IdNode id, String funcId){
        this.type = type;
        this.id = id;
        this.funcId = funcId;
    }

    /**
     * parses a Variable Declaration Node given the list of remaining tokens
     * 
     * @param tokens                    arraylist of tokens
     * @return                          Defined Variable Declaratio Node
     * @throws SyntaxErrorException     one of Child Nodes was incorrect
     */
    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens, String funcId) throws SyntaxErrorException {
        if(tokens.isEmpty()) {
            throw new SyntaxErrorException("Unexpected EOF", JottParser.lastToken);
        }

        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        IdNode idNode = IdNode.parseId(tokens);

        
        
        Token semiColon = tokens.remove(0);
        if(semiColon.getTokenType()==TokenType.SEMICOLON){
            return new VarDecNode(typeNode, idNode, funcId);        
        } else {
            throw new SyntaxErrorException("Expected SemiColon", semiColon );
        }
    }

    @Override
    public String convertToJott() {
        return type.convertToJott() + " " + id.convertToJott() + ";";
    }

    @Override
    public String convertToJava(String className) {
        if(type.toString().equals("String"))
            return type.convertToJava(className) + " " + id.convertToJava(className) + " =\"\";\n";
        else{
            return type.convertToJava(className) + " " + id.convertToJava(className) +";";
        }
    }

    @Override
    public String convertToC() {
        if(type.toString().equals(("String"))){
            return "char *" + id.convertToC() + ";\n";
        }else if(type.toString().equals("Bool")){
            return "int" + " " + id.convertToC() + ";\n";
        } else {
            return type.convertToC() + " " + id.convertToC() + ";\n";
        }
    }

    @Override
    public String convertToPython(int tabs) {
        return id.convertToPython(tabs) + ";\n";
    }

    @Override
    public boolean validateTree() throws SemanticErrorException {
        if(JottParser.symTable.varSymTab.containsKey(id.toString()) && JottParser.symTable.varSymTab.get(id.toString())[0].equals(funcId)){
            throw new SemanticErrorException("Variable already declared", id.getToken());
        }
        
        String[] varData = {type.toString(), funcId};
        JottParser.symTable.varSymTab.put(id.toString(), varData);
        return true;
    }
    
    
}
