import edu.polytechnique.xvm.asm.opcodes.*;
import java.util.Optional;

@SuppressWarnings("unused")
public final class IAssign extends AbstractInstruction {
  public final Optional<String> lvalue; // (optional) left-value
  public AbstractExpr           rvalue; // right-value (expression)

  public IAssign(Optional<String> lvalue, AbstractExpr rvalue) {
    this.lvalue = lvalue;
    this.rvalue = rvalue;
  }

  @Override
  public void codegen(CodeGen cg) {
    throw new UnsupportedOperationException(); // FIXME
  }
}
