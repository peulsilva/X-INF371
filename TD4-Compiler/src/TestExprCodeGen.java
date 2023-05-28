import edu.polytechnique.mjava.parser.MJavaParseError;
import edu.polytechnique.mjava.toplevel.MJavaTop.ExprWithCtxt;
import edu.polytechnique.mjava.typing.EVarInit;
import edu.polytechnique.mjava.typing.exn.MJavaTypingError;
import edu.polytechnique.xvm.asm.opcodes.PUSH;
import edu.polytechnique.xvm.asm.opcodes.STOP;

public class TestExprCodeGen {
  // Modify this expression at will
  public final static String DEFAULT =
    "< x = 2; y = 3;>" + // Local variables declaration
    "x + y*10 >= 30";
    // "print x;";
  
  public static void main(String[] args)
      throws MJavaParseError, MJavaTypingError
  {
    // Get input expression
    String input = args.length == 0 ? DEFAULT : String.join(" ", args);
    
    // Parse & type input expression
    ExprWithCtxt<AbstractExpr> ce = ParseAndType.parseAndTypeExpr(input);

    // Create code generator
    CodeGen cg = new CodeGen();

    // Push local variables initialization
    // (in order of declaration from left-to-right)
    int offset = 0;
    
    for (EVarInit vinit : ce.getCtxt()) {
      cg.pushLocalVariable(vinit.getName(), offset++);
      cg.pushInstruction(new PUSH(vinit.getValue()));
    }

    // Generate code for expression `e'
    ce.getExpr().codegen(cg);

    // Display generated XVM code
    cg.pushInstruction(new STOP());
    System.out.print(cg.toString());
  }
}
