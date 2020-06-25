package app.model;

public class DefaultModelData implements ModelData {

    private String message;

    public DefaultModelData(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
