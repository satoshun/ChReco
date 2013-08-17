package jp.satoshun.chreco.feed;


public class FeedEntry {
    private String title;
    private String link;

    public FeedEntry(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
