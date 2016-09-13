package paysafe.interns.models;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Doc {
    //TODO: Store the file extension
    private String name;
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
