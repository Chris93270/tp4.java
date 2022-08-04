import java.util.Date;
import java.util.List;

public class Document {
    private int documentID;
    private String documentName;
    private Date documentDate;
    private String storageAdresse;

    private int Category;
    private Topic topic;
    private List<Tag> tags;

    public Document(int documentID, String documentName, Date documentDate, String storageAdresse) {
        this.documentDate = documentDate;
        this.documentID = documentID;
        this.storageAdresse = storageAdresse;
        this.documentName = documentName;
    }

    public int getDocumentID() {
        return documentID;
    }

    public void setDocumentID(int documentID) {
        this.documentID = documentID;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getStorageAdresse() {
        return storageAdresse;
    }

    public void setStorageAdresse(String storageAdresse) {
        this.storageAdresse = storageAdresse;
    }
}