package cn.itcast.domain;

public class stu {
    private String name;
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "stu{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
