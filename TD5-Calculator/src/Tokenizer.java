public class Tokenizer {
    boolean isStart;
    boolean isIntNum;
    double num;
    Calculator calc;
    boolean isNonIntNum;
    int decimalDigits;
    int sign;

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

        if (this.isStart){
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

            else if (c == '-')
                this.sign = -1;
            
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
    }

    private void resetState(){
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
        tokenizer.readString("(25-5)-(28-13)=");
        System.out.println(tokenizer.calc.results);
    }
}
