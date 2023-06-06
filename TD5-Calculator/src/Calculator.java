import java.util.Stack;

public class Calculator {
    Stack<Double> numbers;
    Stack<Operator> operators;

    public Calculator(){
        this.numbers = new Stack<Double>();
        this.operators = new Stack<Operator>();
    }

    public void pushDouble(double d){
        this.numbers.push(d);
    }

    public void pushOperator (Operator op){
        this.operators.push(op);
    }

    public double getResult(){
        if (this.numbers.empty())
            throw new RuntimeException("Trying to get the top of an empty stack.");
        return this.numbers.firstElement();
    }

    public void executeBinOperator(Operator op){
        double a = this.numbers.pop();
        double b = this.numbers.pop();
        double result;

        switch (op){
            case PLUS:
                result = a + b;
                break;
            
            case MINUS:
                result = b - a;
                break;

            case MULT:
                result = a * b;
                break;

            case DIV: 
                result = b/a;
                break;

            default:
                result = 0;

        }

        
        this.numbers.push(result);
    }

    private int precedece(Operator op){
        switch (op){
            case PLUS: 
                return 1;
            
            case MINUS: 
                return 1;
            
            case MULT: 
                return 2;
            
            case DIV: 
                return 2;
            case OPEN:
                break;
  
        }

        throw new RuntimeException("Error in precedence: not implemented operator");
    }

    public void commandOperator(Operator op){
        if (this.operators.empty()){
            this.operators.push(op);
            return;
        }

        Operator lastOp = this.operators.lastElement();

        while (!this.operators.empty() &&
               this.precedece(op) <= this.precedece(lastOp)){
            
            Operator thisOp = this.operators.pop();
            this.executeBinOperator(thisOp);
            
            if (!this.operators.empty())
                lastOp = this.operators.lastElement();
        }

        this.operators.push(op);
        
    }

    public void commandDouble(double d){
        this.numbers.push(d);
    }

    public void commandEqual(){
        while (!this.operators.empty()){
            Operator op = this.operators.pop();
            this.executeBinOperator(op);

        }
    }

    public void commandLpar(){
        this.operators.push(Operator.OPEN);
    }

    public void commandRpar(){
        if (this.operators.empty()){

        }
        Operator lastOp = this.operators.firstElement();
        while (!this.operators.empty() &&
               lastOp != Operator.OPEN){
            
            
        }
    }

    @Override
    public String toString() {
        return this.numbers.toString() + "\n" +
               this.operators.toString();
    }

    public static void main (String[] args){
        Calculator calculator = new Calculator();

        calculator.commandDouble(5);
        calculator.commandOperator(Operator.PLUS);
        calculator.commandDouble(8);
        calculator.commandOperator(Operator.DIV);
        calculator.commandDouble(9);;
        calculator.commandEqual();

        System.out.println(calculator.getResult());
        System.out.println(calculator);
    }

}
