public class Tokenizer {
    boolean isStart;
    boolean isIntNum;
    double num;
    Calculator calc;

    public Tokenizer(){
        this.isStart = true;
        this.isIntNum = false;
        this.calc = new Calculator();
    }

    public void readChar(char c){
        if (this.isStart && Character.isDigit(c)){
            this.num = Character
                .getNumericValue(c);
        
            this.isIntNum = true;
            this.isStart = false;
        }
        
        else if (this.isIntNum){
            if (Character.isDigit(c)){
                int thisAlg = Character 
                    .getNumericValue(c);
                this.num = 10*this.num + thisAlg;
            }
        

            else if ( ! Character.isDigit(c)){
                this.calc.commandDouble(num);
                if (Character.compare(c, '=') == 0){
                    this.isStart = true;
                    this.isIntNum = false;
                    this.calc.commandEqual();
                }
            }
        }

        
    }

    public static void main(String [] args){
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.readChar('1');
        tokenizer.readChar('2');
        tokenizer.readChar('=');
        tokenizer.readChar('2');
        tokenizer.readChar('3');
        tokenizer.readChar('=');
        System.out.println(tokenizer.calc.results);
    }
}
