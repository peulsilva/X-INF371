import edu.polytechnique.xvm.asm.opcodes.*;
import edu.polytechnique.mjava.ast.BinOp;

@SuppressWarnings("unused")
public final class EBinOp extends AbstractExpr {
  public final BinOp        op   ;    // operator (enum)
  public final AbstractExpr left ;    // left operand
  public final AbstractExpr right;    // right operand

  public EBinOp(BinOp op, AbstractExpr left, AbstractExpr right) {
    this.op = op;
    this.left = left;
    this.right = right;
  }

  @Override
  public void codegen(CodeGen cg) {
    throw new UnsupportedOperationException(); // FIXME
  }
}
