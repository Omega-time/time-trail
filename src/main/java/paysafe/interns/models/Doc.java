package paysafe.interns.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Class representing an uploaded file (Doc) to the database. Not to be
 * used on its own, currently implemented exclusively as a collection
 * in {@link Project}
 */
@Embeddable
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@documentId")
public class Doc {
    /** The name of the uploaded doc */
    private String name;
    /** The content type of the uploaded doc */
    private String type;
    /** The file itself as a binary array, stored in the database */
    @Lob
    private byte[] file;

    private long fileSizeKb;


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


    public long getFileSizeKb() {
        return fileSizeKb;
    }

    public void setFileSizeKb(long fileSizeKb) {
        this.fileSizeKb = fileSizeKb;
    }

    @Override public String toString() {
        return "Doc{" +
                "name='" + name + '\'' +
                ", file size=" + file.length/1024 + " KB"+
                '}';
    }
}
