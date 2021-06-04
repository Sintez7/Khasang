package sample;

public class Project implements ProjectUnit {

    private int id;
    private String projectName;
    private ProjectTaskManager taskManager;
    private Employee resp;


    public Project() {
//        this("UnknownProject");
    }

//    public Project (String name) {
//
//    }

    public Project (int id, String projectName) {

    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public String getCurTaskDesc() {
        return taskManager.getCurTaskDesc();
    }

    @Override
    public String getRespName() {
        return resp.getName();
    }

    @Override
    public String getRespPhoneOrEMail() {
        return resp.getContactEMail();
    }

    @Override
    public String getTaskStartDate() {
        return taskManager.getCurTaskStartDate();
    }

    @Override
    public String getTaskLength() {
        return taskManager.getTaskLength();
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
