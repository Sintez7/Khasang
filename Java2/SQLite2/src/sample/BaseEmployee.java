package sample;

public class BaseEmployee implements Employee {

    private final String name;
    private final String eMail;
    private final String phone;

    public BaseEmployee (String name, String eMail, String phone) {
        this.name = name;
        this.eMail = eMail;
        this.phone = phone;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContactEMail() {
        return eMail;
    }

    @Override
    public String getContactPhone() {
        return phone;
    }
}
