package top.liaoxyucm;

import top.liaoxyucm.ssdotjava.Ssdot;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static boolean cts(List<String> lst, String verbose, String simple) {
    return lst.contains("--"+verbose) || lst.contains("-"+simple);
  }

  public static void main(String[] args) {
    List<String> argl = Arrays.asList(args);
    if (cts(argl, "version", "v")) {
      System.out.println("ssdot v1.3.0");
      return;
    }
    boolean keepResidue = cts(argl, "keepResidue", "kr");
    boolean keepStatus = cts(argl, "keepStatus", "ks");
    boolean trimEnd = cts(argl, "trimEnd", "te");

    Scanner scanner = new Scanner(System.in);
    Ssdot ssdotobj = new Ssdot(keepResidue, keepStatus);
    
    System.out.println("Press Ctrl/Cmd + C to exit.");
    while (true) {
      try {
        String text = scanner.nextLine();
        String result = ssdotobj.ssdotCompile(text);
        if (trimEnd) {
          result = result.replaceAll("\\s+$", "");
        }
        System.out.println(result);
      } catch (Exception e) {
        break;
      }
    }
    scanner.close();
  }
}
