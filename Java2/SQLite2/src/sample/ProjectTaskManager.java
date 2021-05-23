package sample;

import java.util.ArrayList;

public class ProjectTaskManager {

    private static final String NO_TASK = "На текущий момент нет исполняемой задачи";

    private final ArrayList<ProjectTask> taskList = new ArrayList<>();

    public String getCurTaskDesc() {
        final ProjectTask validTask = getValidTask();
        if (validTask != null) {
            return validTask.getDescription();
        }
        return NO_TASK;
    }

    public void addTask(ProjectTask task) {
        taskList.add(task);
    }

    public String getCurTaskStartDate() {
        final ProjectTask validTask = getValidTask();
        if (validTask != null) {
            return validTask.getStartDate();
        }
        return NO_TASK;
    }

    private ProjectTask getValidTask() {
        if (taskList.size() > 0) {
            for (ProjectTask projectTask : taskList) {
                if (!projectTask.isDone() & !projectTask.isExpired()) {
                    return projectTask;
                }
            }
        }
        return null;
    }

    public String getTaskLength() {
        final ProjectTask validTask = getValidTask();
        if (validTask != null) {
            return validTask.getLength();
        }
        return NO_TASK;
    }
}
