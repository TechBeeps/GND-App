package quran.gnd.com;
public class DuaClass {
    private String name,source,translation,dua;
    public DuaClass(){}
    public DuaClass(String name, String source, String translation, String dua){this.name = name;this.source = source;this.translation = translation;this.dua = dua;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }
    public String getDua() {
        return dua;
    }
    public void setDua(String dua) {
        this.dua = dua;
    }
}
