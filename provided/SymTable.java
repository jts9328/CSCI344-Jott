package provided;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SymTable {
    // Format: <Variable Name, Variable Type>
    public HashMap<String, String> varSymTab;
    // Format: <Function Name, ArrayList<Parameter Types*, Return Type>>
    public HashMap<String, ArrayList<String>> funcSymTab;

    public SymTable() {
        this.funcSymTab = new HashMap<>();
        funcSymTab.put("print", new ArrayList<>(Arrays.asList("Any", "Void")));
        this.varSymTab = new HashMap<>();

        System.out.println();
    }

    
}
