package jp.satoshun.chreco.feed;


import java.util.ArrayList;
import java.util.List;

public class FeedData {
    private List<FeedEntry> entries;
    
    public FeedData() {
        entries = new ArrayList<FeedEntry>();
    }

    public void set(FeedEntry entry) {
        entries.add(entry);
    }

    public void set(String title, String link) {
        entries.add(new FeedEntry(title, link));
    }
}
