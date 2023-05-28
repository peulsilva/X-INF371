import edu.polytechnique.xvm.asm.opcodes.*;

public final class IWhile extends AbstractInstruction {
  public final AbstractExpr        condition; // loop condition
  public final AbstractInstruction body     ; // loop body

  public IWhile(AbstractExpr condition, AbstractInstruction body) {
    this.condition = condition;
    this.body = body;
  }

  @Override
  public void codegen(CodeGen cg) {
    String alpha = CodeGen.generateLabel();
    String beta = CodeGen.generateLabel();

    // Push start of loop label
    cg.pushLabel(alpha);
    // Assemble condition
    this.condition.codegen(cg);
    // If condition is 0, jump to beta
    cg.pushInstruction(new GTZ(beta));
    // Generate code for the body
    this.body.codegen(cg);
    // looping
    cg.pushInstruction(new GTO(alpha));
    cg.pushLabel(beta);
    
  }
}
