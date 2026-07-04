package top.liaoxyucm;

import top.liaoxyucm.ssdotjava.Ssdot;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    List<String> arguments = Arrays.asList(args);
    if (arguments.contains("--version") || arguments.contains("-v")) {
      System.out.println("ssdot v1.2.0");
      return;
    }
    boolean keepResidue = arguments.contains("--keepResidue") || arguments.contains("-kr");
    boolean keepStatus = arguments.contains("--keepStatus") || arguments.contains("-ks");
    System.out.println("Press Ctrl/Cmd + C to exit.");
    Scanner scanner = new Scanner(System.in);
    Ssdot ssdotobj = new Ssdot(keepResidue, keepStatus);
    while (true) {
      try {
        String text = scanner.nextLine();
        String result = ssdotobj.ssdotCompile(text);
        System.out.println(result);
      } catch (Exception e) {
        break;
      }
    }
    scanner.close();
  }
}
