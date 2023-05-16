import java.awt.Font;

import typo.FixedSpace;
import typo.Glyph;
import typo.RelativeSpace;
import typo.Space;


public class Test {
  static void test2() {
    Font f = new Font("SansSerif", Font.PLAIN, 70);
    Glyph g = new Glyph(f, 'g');
    System.out.println(g);
  }



  static void test3() {
    Font f = new Font("SansSerif", Font.PLAIN, 70);
    Glyph g = new Glyph(f, 'g');
    System.out.println(g);
    new Page(g, 150, 150);
  }

  static void test5(){
    Font font = new Font("SansSerif", Font.PLAIN, 70);
    Space s = new Space(2, 3);
    FixedSpace fs = new FixedSpace(5);
    RelativeSpace rs = new RelativeSpace(4, font);

    System.out.println(s);
    System.out.println(fs);
    System.out.println(rs);
  }

  public static void main (String[] args){
    // test2();
    // test3();
    test5();
  }
}
