package paysafe.interns.models;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Doc {
    private String name;
    private String type;
    @Lob
    private byte[] file;

    public Doc() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override public String toString() {
        return "Doc{" +
                "name='" + name + '\'' +
                ", file size=" + file.length/1024 + " KB"+
                '}';
    }
}
