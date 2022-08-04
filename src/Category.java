public class Category {
    private int categoryID;
    private String nom;

  public  Category(int categoryID, String nom){
      this.categoryID=categoryID;
      this.nom=nom;
  }

    public int getCategoryID() {
        return categoryID;
    }
    public String getNom() {
        return nom;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
