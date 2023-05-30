import java.util.List;

import edu.polytechnique.mjava.ast.TProcDef;
import edu.polytechnique.mjava.ast.VarDecl;
import edu.polytechnique.xvm.asm.opcodes.GSB;
import edu.polytechnique.xvm.asm.opcodes.RET;
import edu.polytechnique.xvm.asm.opcodes.STOP;

public class ProgramCodeGen {
  public final CodeGen cg = new CodeGen();
  int offset = 0;

  public static String labelOfProcName(String name) {
    return String.format("__%s", name);
  }

  @SuppressWarnings("unused")
  public void codegen(TProcDef<AbstractExpr, AbstractInstruction> proc) {
    final List<VarDecl> args = proc.getArgs(); // Proc. arguments
    final List<VarDecl> locals = proc.getLocals(); // Proc. locals
    final AbstractInstruction is = proc.getBody(); // Proc. body

    
    String procName = ProgramCodeGen
      .labelOfProcName(proc.getName());

    cg.pushLabel(procName);
    
    for (VarDecl arg : args){
      this.offset++;
      String argName = arg.getName();
      cg.pushLocalVariable(argName, this.offset);
    }

    for (VarDecl localVar: locals){
      this.offset++;
      String varName = localVar.getName();
      cg.pushLocalVariable(varName, this.offset); 
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
