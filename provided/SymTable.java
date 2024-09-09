package provided;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SymTable {
    // Format: <Variable Name, [Variable Type, funcOfOriginId]>
    public HashMap<String, String[]> varSymTab;
    // Format: <Function Name, ArrayList<Parameter Types*, Return Type>>
    public HashMap<String, ArrayList<String>> funcSymTab;

    public SymTable() {
        this.funcSymTab = new HashMap<>();
        funcSymTab.put("print", new ArrayList<>(Arrays.asList("Any", "Void")));
        funcSymTab.put("concat", new ArrayList<>(Arrays.asList("String", "String", "String")));
        funcSymTab.put("length", new ArrayList<>(Arrays.asList("String", "Integer")));
        this.varSymTab = new HashMap<>();

        System.out.println();
    }

    
}
