package top.liaoxyucm;

import top.liaoxyucm.ssdotjava.Ssdot;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    boolean keepResidue = Arrays.asList(args).contains("--keepResidue");
    System.out.println("Press Ctrl/Cmd + C to exit.");
    Scanner scanner = new Scanner(System.in);
    Ssdot ssdotobj = new Ssdot();
    while (true) {
      try {
        String text = scanner.nextLine();
        String result = ssdotobj.ssdotCompile(text, keepResidue);
        System.out.println(result);
      } catch (Exception e) {
        break;
      }
    }
    scanner.close();
  }
}
