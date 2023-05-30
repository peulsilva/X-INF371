import java.util.List;

import edu.polytechnique.mjava.ast.TProcDef;
import edu.polytechnique.mjava.ast.VarDecl;
import edu.polytechnique.xvm.asm.opcodes.GSB;
import edu.polytechnique.xvm.asm.opcodes.PUSH;
import edu.polytechnique.xvm.asm.opcodes.RET;
import edu.polytechnique.xvm.asm.opcodes.STOP;

public class ProgramCodeGen {
  public final CodeGen cg = new CodeGen();
  
  public static String labelOfProcName(String name) {
    return String.format("__%s", name);
  }
  
  @SuppressWarnings("unused")
  public void codegen(TProcDef<AbstractExpr, AbstractInstruction> proc) {
    final List<VarDecl> args = proc.getArgs(); // Proc. arguments
    final List<VarDecl> locals = proc.getLocals(); // Proc. locals
    final AbstractInstruction is = proc.getBody(); // Proc. body
    
    // for function arguments, the addres related to the FP is -1, -2, ..., -n
    int offset = -args.size();
    String procName = ProgramCodeGen
      .labelOfProcName(proc.getName());

    cg.pushLabel(procName);
    
    for (VarDecl arg : args){
      String argName = arg.getName();
      cg.pushLocalVariable(argName, offset);
      offset ++;
    }

    // for local variables, the address related to the FP is 2, 3, ..., n+2
    offset = 2;
    for (VarDecl localVar: locals){
      
      String varName = localVar.getName();
      cg.pushLocalVariable(varName, offset); 
      cg.pushInstruction(new PUSH(0));
      offset ++;
    }

    is.codegen(cg);
    cg.clearLocals();
    
    if (!proc.getRtype().isPresent()){
      cg.pushInstruction(new RET());
    }
  }

  public void codegen(List<TProcDef<AbstractExpr, AbstractInstruction>> procs) {
    for (TProcDef<AbstractExpr, AbstractInstruction> proc : procs)
      this.codegen(proc);
  }

  public ProgramCodeGen() {
    this.cg.pushInstruction(new GSB(labelOfProcName("main")));
    this.cg.pushInstruction(new STOP());
  }
}
