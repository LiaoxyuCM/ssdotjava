package top.liaoxyucm.ssdotjava;

public class Ssdot {
  private boolean keepResidue = false;
  private boolean keepStatus = false;
  private StringBuilder storedString = new StringBuilder("");
  private int status = 0;

  public Ssdot() {}
  public Ssdot(boolean keepResidue) {
    this.keepResidue = keepResidue;
  }
  public Ssdot(boolean keepResidue, boolean keepStatus) {
    this.keepResidue = keepResidue;
    this.keepStatus = keepStatus;
  }


  public String ssdotCompile(String text, boolean keepResidue, boolean keepStatus) {
    StringBuilder result = new StringBuilder("");
    for (char c : text.toCharArray()) {
      if (this.status == 1) {
        this.storedString.append(c);
        this.status = 0;
        continue;
      }
      switch (c) {
        case '.':
          if (this.status == 0) {
            result.append(this.storedString.toString() + '\n');
            this.storedString.setLength(0);
          }
          break;

        case '\\':
          if (this.status == 0) {
            this.status = 1;
          }
          break;

        case '/':
          if (this.status == 0) {
            this.status = 2;
          } else if (this.status == 2) {
            this.status = 0;
          }
          break;

        default:
          if (this.status == 0) {
            this.storedString.append(c);
          }
          break;
      }
    }
    if (!keepResidue) {
      this.storedString.setLength(0);
    }
    if (!keepStatus) {
      this.status = 0;
    }

    return result.toString();
  }

  public String ssdotCompile(String text) {
    return this.ssdotCompile(text, this.keepResidue, this.keepStatus);
  }
  public String ssdotCompile(String text, boolean keepResidue) {
    return this.ssdotCompile(text, keepResidue, this.keepStatus);
  }
}
