package top.liaoxyucm;

import top.liaoxyucm.ssdotjava.Ssdot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

public class Main {
  private static List<String> argl = Arrays.asList();
  private static List<String> affirmativeExpr = Arrays.asList("y", "yes", "t", "true", "please", "plz", "pls", "on");
  private static HashMap<String, Boolean> cargs = new HashMap<String, Boolean>();
  private static String[][] argTransAlias = {
    {"keepResidue", "kr"},
    {"keepStatus", "ks"},
    {"trimEnd", "te"},
    {"verbose", "vb"}
  };

  private static boolean str2bool(String str) {
    return affirmativeExpr.contains(str.trim().toLowerCase());
  }
  private static String simple2verbose(String str) {
    for (String[] c : argTransAlias) {
      if (c[1].equals(str.trim())) { return c[0]; }
    }
    return str;
  }
  private static boolean cts(String verbose, String simple) {
    return argl.contains("--"+verbose) || argl.contains("-"+simple);
  }

  private static String genconf(boolean simple) {
    if (simple) {
      return String.format(
        "kr:%s\nks:%s\nte:%s\nvb:%s",
        cargs.get("keepResidue") ? "t" : "f",
        cargs.get("keepStatus") ? "t" : "f",
        cargs.get("trimEnd") ? "t" : "f",
        cargs.get("verbose") ? "t" : "f"
      );
    } else {
      return String.format(
        "keepResidue: %s\nkeepStatus: %s\ntrimEnd: %s\nverbose: %s",
        cargs.get("keepResidue") ? "true" : "false",
        cargs.get("keepStatus") ? "true" : "false",
        cargs.get("trimEnd") ? "true" : "false",
        cargs.get("verbose") ? "true" : "false"
      );
    }
  }
  public static void main(String[] args) {
    argl = Arrays.asList(args);
    for (String[] c : argTransAlias) {
      cargs.put(c[0], cts(c[0], c[1]));
    }
    if (cts("version", "v")) {
      System.out.println("ssdot v1.5.0");
      return;
    }
    
    Scanner scanner = new Scanner(System.in);
    try {
      if (argl.contains("genconf")) {
        System.out.print("Which mode? [STANDARD/simple] ");
        Files.write(Paths.get(".ssdotconf"), genconf(scanner.nextLine() == "simple").getBytes());
        return;
      } else if (argl.contains("genconf:standard")) {
        Files.write(Paths.get(".ssdotconf"), genconf(false).getBytes());
        return;
      } else if (argl.contains("genconf:simple")) {
        Files.write(Paths.get(".ssdotconf"), genconf(true).getBytes());
        return;
      }
    } catch (IOException e) {
      System.out.println("Cannot write file");
      return;
    }
    
    File fileconf = new File(".ssdotconf");
    if (fileconf.exists()) {
      try {
        List<String> lines = Files.readAllLines(Paths.get(".ssdotconf"));
        for (String line : lines) {
          String[] contents = line.split(":");
          if (contents.length < 2) {
            System.out.println("Invalid syntax");
            return;
          }
          if (contents[1] != null && !contents[1].isBlank()) {
            cargs.put(simple2verbose(contents[0]), str2bool(contents[1]));
          }
        }
      } catch (IOException e) {
        System.out.println("Cannot read file");
        return;
      }
    }

    Ssdot ssdotobj = new Ssdot(
      cargs.get("keepResidue"),
      cargs.get("keepStatus")
    );
    
    System.out.println("Press Ctrl/Cmd + C to exit.");
    while (true) {
      try {
        if (cargs.get("verbose")) {
          System.out.print(String.format(
            "[%s %s]> ",
            cargs.get("keepResidue") ? String.valueOf(ssdotobj.storedString().length()) : "~",
            cargs.get("keepStatus") ? String.valueOf(ssdotobj.status.code()) : "~"
          ));
        }
        String text = scanner.nextLine();
        String result = ssdotobj.ssdotCompile(text);
        if (cargs.get("trimEnd")) {
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
