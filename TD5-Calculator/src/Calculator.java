import java.util.LinkedList;
import java.util.Stack;


public class Calculator {
    Stack<Double> numbers;
    Stack<Operator> operators;
    LinkedList<Double> results;

    public Calculator(){
        this.numbers = new Stack<Double>();
        this.operators = new Stack<Operator>();
        this.results = new LinkedList<Double>();
    }

    public void pushDouble(double d){
        this.numbers.push(d);
    }

    public void pushOperator (Operator op){
        this.operators.push(op);
    }

    public double getResult(){
        if (this.numbers.empty())
            throw new RuntimeException("Error on getResult: Trying to get the result from the top of an empty stack.");
        double result = this.numbers.pop();
        this.numbers.push(result);
        return result;
    }

    public void executeBinOperator(Operator op){
        if (this.numbers.empty())
            throw new RuntimeException("error on executeBinOperstor: numbers with less than one element. This might happen as a result of incorrect parenthesis");
        double a = this.numbers.pop();
        
        if (this.numbers.empty())
            throw new RuntimeException("error on executeBinOperstor: numbers with less than two elements. This might happen as a result of incorrect parenthesis");
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
                result = -1e4;
                break;

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
                return 0;
  
        }

        throw new RuntimeException("Error in precedence: not implemented operator");
    }

    public void commandOperator(Operator op){
        if (this.operators.empty()){
            this.operators.push(op);
            return;
        }

        Operator lastOp = this.operators.lastElement();

        if (op == Operator.OPEN)
            return;

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
        double result = this.numbers.lastElement();
        this.results.push(result);
    }

    public void commandLPar(){
        this.operators.push(Operator.OPEN);
    }

    public void commandRPar(){
        if (this.operators.empty()){
            throw new RuntimeException("adding a right parenthesis without having a left one before");
        }
        Operator lastOp = this.operators.lastElement();
        while (!this.operators.empty() &&
               lastOp != Operator.OPEN){
            Operator op = this.operators.pop();
            this.executeBinOperator(op);
            lastOp = this.operators.lastElement();
        }

        // here, lastOp == OPEN
        // Discard matching parenthesis
        this.operators.pop();

    }

    public void commandInit(){
        this.numbers.clear();
        this.operators.clear();
    }

    public void commandReadMemory(int i){
        if (this.results.size() < i)
            throw new RuntimeException("Trying to read memory from an inexisting space");
        double iResult = this.results.get(results.size() - i);
        this.numbers.push(iResult);
    }

    @Override
    public String toString() {
        return this.numbers.toString() + "\n" +
               this.operators.toString();
    }

    public static void main (String[] args){
        Calculator calculator = new Calculator();

        calculator.commandLPar();
        calculator.commandDouble(5);
        calculator.commandOperator(Operator.MINUS);
        calculator.commandDouble(4);
        calculator.commandRPar();
        calculator.commandOperator(Operator.MULT);
        calculator.commandLPar();
        calculator.commandDouble(8);
        calculator.commandOperator(Operator.PLUS);
        calculator.commandDouble(-5);
        calculator.commandRPar();
        calculator.commandEqual();

        System.out.println(calculator.getResult());
        System.out.println(calculator.results);
    }

}
