package top.liaoxyucm.ssdotjava;

public class Ssdot {
  public enum Status {
    NORMAL(0),
    BACKSLASH(1),
    COMMENT(2);

    private final int code;

    Status(int code) {
      this.code = code;
    }

    public int code() {
      return this.code;
    }
  }

  public boolean keepResidue = false;
  public boolean keepStatus = false;
  public Status status = Status.NORMAL;
  
  private StringBuilder storedString = new StringBuilder("");
  public String storedString() {
    return this.storedString.toString();
  }

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
      if (this.status == Status.BACKSLASH) {
        this.storedString.append(c);
        this.status = Status.NORMAL;
        continue;
      }
      switch (c) {
        case '.':
          if (this.status == Status.NORMAL) {
            result.append(this.storedString.toString() + '\n');
            this.storedString.setLength(0);
          }
          break;

        case '\\':
          if (this.status == Status.NORMAL) {
            this.status = Status.BACKSLASH;
          }
          break;

        case '/':
          if (this.status == Status.NORMAL) {
            this.status = Status.COMMENT;
          } else if (this.status == Status.COMMENT) {
            this.status = Status.NORMAL;
          }
          break;

        default:
          if (this.status == Status.NORMAL) {
            this.storedString.append(c);
          }
          break;
      }
    }
    if (!keepResidue) {
      this.storedString.setLength(0);
    }
    if (!keepStatus) {
      this.status = Status.NORMAL;
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
