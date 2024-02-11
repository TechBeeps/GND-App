package quran.gnd.com;
public class PostClass {
    private String name,dated,image,details;
    public PostClass(){}
    public PostClass(String name, String dated, String image, String details) {this.name = name;this.dated = dated;this.image = image;this.details = details;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDated() {return dated;}
    public void setDated(String dated) {this.dated = dated;}
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}
    public String getDetails() {return details;}
    public void setDetails(String details) {this.details = details;}
}
