package quran.gnd.com;

public class Users {
    String name,email,phone,refferal,amount,refered_by,point;
    public Users() {}
    public Users(String name, String email, String phone, String refferal, String amount, String refered_by,String point) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.refferal = refferal;
        this.amount = amount;
        this.refered_by = refered_by;
        this.point = point;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefferal() {
        return refferal;
    }

    public void setRefferal(String refferal) {
        this.refferal = refferal;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getRefered_by() {return refered_by;}
    public void setRefered_by(String refered_by) {this.refered_by = refered_by;}

}
