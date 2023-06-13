package com.td5;
import java.util.Arrays;

public class Tokenizer {
    boolean isStart;
    boolean isIntNum;
    double num;
    Calculator calc;
    boolean isNonIntNum;
    int decimalDigits;
    int sign;
    char lastChar;

    private boolean debug;

    public Tokenizer(){
        this.isStart = true;
        this.isIntNum = false;
        this.calc = new Calculator();
        this.debug = false;
        this.isNonIntNum = false;
        this.decimalDigits = 0;
        this.sign = 1;
    }

    public Tokenizer(boolean debug){
        this.isStart = true;
        this.isIntNum = false;
        this.calc = new Calculator();
        this.debug = debug;
    }

    public void readChar(char c){
        if (c == ' ')
            return;

        if (c == 'C'){
            this.resetState();
            this.calc = new Calculator();
            return;
        }

        if (this.isStart){
            boolean isNegativeNumber = this.isNegativeNumber(c);

            if (Character.isDigit(c)){
                int n = Character.getNumericValue(c);
                
                if (this.isNonIntNum){
                    this.num = n * Math.pow(10, -this.decimalDigits);
                    this.decimalDigits++;
                }
                else    
                    this.num = n;
            
                this.isIntNum = !this.isNonIntNum;
                this.isStart = false;
            }
            else if (c == '.'){
                this.isNonIntNum = true;
                this.decimalDigits++;
            }

            else if (isNegativeNumber){
                this.sign = -1 ;
            }
            else
                this.handleCommand(c);
        }
        
        else if (this.isIntNum && !this.isNonIntNum){
            if (Character.isDigit(c)){
                int thisAlg = Character 
                    .getNumericValue(c);
                this.num = 10*this.num + thisAlg;
            }
        
            else {
                if (c == '.'){
                    this.isNonIntNum = true;
                    this.decimalDigits++;
                }
                else{

                    this.calc.commandDouble(num * this.sign);
                    this.resetState();
    
                    this.handleCommand(c);
                }
            }
        }

        else if (this.isNonIntNum){

            if (Character.isDigit(c)){

                int thisAlg = Character 
                    .getNumericValue(c);
                this.num += thisAlg * Math.pow(10, -this.decimalDigits);
                this.decimalDigits++;
            }

            else{
                this.calc.commandDouble(num * this.sign);
                this.resetState();

                this.handleCommand(c);
            }
        }
        this.lastChar = c;
    }

    
    /**
     * Resets states of everything, unless the calculator
     **/
    public void resetState(){
        
        this.isStart = true;
        this.isIntNum = false;
        this.isNonIntNum = false;
        this.decimalDigits = 0;
        this.sign = 1;
    }

    private void handleCommand(char c){
        if (c == '=')
            this.calc.commandEqual();

        else if (c =='+')
            this.calc.commandOperator(Operator.PLUS);

        else if (c == '-')
            this.calc.commandOperator(Operator.MINUS);
        
        else if (c == '*')
            this.calc.commandOperator(Operator.MULT);

        else if (c == '/')
            this.calc.commandOperator(Operator.DIV);
        
        else if (c == '(')
            this.calc.commandLPar();
        
        else if (c == ')')
            this.calc.commandRPar();
        
        else
            throw new RuntimeException("error in Tokenizer.readChar(char): not recognized char " + c);
    }

    private boolean isBinaryOperator(char c){
        char[] binaryOperators = {'+', '-', '*', '/'};
        return (new String(binaryOperators).indexOf(c) != -1);
    }

    private boolean isNegativeNumber (char c){
        boolean isNegativeNumber = this.lastChar == 0 
            || this.lastChar == '(' 
            || this.lastChar == '='
            || this.isBinaryOperator(this.lastChar);

        isNegativeNumber = isNegativeNumber && c == '-';

        return isNegativeNumber;
    }

    public void readString(String s){
        for (int i=0; i < s.length(); i++){
            char c = s.charAt(i);
            this.readChar(c);
        }
    }

    public static void main(String [] args){
        Tokenizer tokenizer = new Tokenizer();
        // tokenizer.readChar('1');
        // tokenizer.readChar('2');
        // tokenizer.readChar('=');
        // tokenizer.readChar('2');
        // tokenizer.readChar('3');
        // tokenizer.readChar('=');
        tokenizer.readString("8+-2=");

        System.out.println(tokenizer.calc.results);
    }
}
