import edu.polytechnique.xvm.asm.opcodes.*;


public final class IIf extends AbstractInstruction {
  public final AbstractExpr        condition; // condition (<> 0 => true)
  public final AbstractInstruction iftrue   ; // if "true  (<> 0)" branch
  public final AbstractInstruction iffalse  ; // if "false (== 0)" branch

  public IIf(AbstractExpr condition,
             AbstractInstruction iftrue,
             AbstractInstruction iffalse)
  {
    this.condition = condition;
    this.iftrue = iftrue;
    this.iffalse = iffalse;
  }

  @Override
  public void codegen(CodeGen cg) {
    // compile the condition
    String alpha = CodeGen.generateLabel();
    String beta = CodeGen.generateLabel();
    
    this.condition.codegen(cg);

    // GTZ(alpha)
    cg.pushInstruction(new GTZ(alpha));

    // compile iftrue condition
    this.iftrue.codegen(cg);
    
    // GTO(beta)
    cg.pushInstruction(new GTO(beta));

    cg.pushLabel(alpha);
    
    // compile iffalse condition
    this.iffalse.codegen(cg);

    cg.pushLabel(beta);
  }
}
