package kg.geektech.taskapp37.models;

import java.io.Serializable;

public class News implements Serializable {

    private String title;
    private long createAt;

    public News(String title, long createAt) {
        this.title = title;
        this.createAt = createAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
}
