package idealab.api.controller;

public class DropboxFile{
  private String name;

  private DropboxFile(){}

  public DropboxFile(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}