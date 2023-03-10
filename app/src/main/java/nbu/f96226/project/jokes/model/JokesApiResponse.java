package nbu.f96226.project.jokes.model;

public class JokesApiResponse {

    private boolean error;
    private String category;
    private String type;
    private String joke;
    private int id;
    private boolean safe;
    private String lang;
    private Flags flags;

    public JokesApiResponse(boolean error, String category, String type, String joke, int id, boolean safe, String lang, Flags flags) {
        this.error = error;
        this.category = category;
        this.type = type;
        this.joke = joke;
        this.id = id;
        this.safe = safe;
        this.lang = lang;
        this.flags = flags;
    }

    public JokesApiResponse(int id, String joke, String category) {
        this.id = id;
        this.joke = joke;
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }
}
