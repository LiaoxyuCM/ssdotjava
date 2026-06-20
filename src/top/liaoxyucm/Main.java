package top.liaoxyucm;

import top.liaoxyucm.ssdotjava.Ssdot;
import java.util.Scanner;
import java.awt.event.KeyEvent;

public class Main {
  public static void main(String[] args) {
    System.out.println("Press Ctrl/Cmd + C to exit.");
    Scanner scanner = new Scanner(System.in);
    while (true) {
      try {
        String text = scanner.nextLine();
        String result = Ssdot.ssdotCompile(text);
        System.out.println(result);
      } catch (Exception e) {
        break;
      }
    }
    scanner.close();
  }
}
