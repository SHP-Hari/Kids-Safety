package lk.applife.kidssafety.model;

public class ArticleTitle {
    private int id;
    private String title;
    private String artId;
    private String artCategory;

    public ArticleTitle(int artId, String arttitle, String articleId, String cat){
        this.id = artId;
        this.title = arttitle;
        this.artId = articleId;
        this.artCategory = cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    }

    public String getArtCategory() {
        return artCategory;
    }

    public void setArtCategory(String artCategory) {
        this.artCategory = artCategory;
    }
}
