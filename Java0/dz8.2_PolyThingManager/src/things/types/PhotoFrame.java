package things.types;

import things.Thing;

public class PhotoFrame extends Thing {

    private String pictureDescription;

    public PhotoFrame() {
        this(0);
    }

    public PhotoFrame(int id) {
        super(id);
        name = "Photo Frame name";
        description = "Some Photo Frame description";
        pictureDescription = "Sunset";
    }

    public String getPictureDescription() {
        return pictureDescription;
    }
}
