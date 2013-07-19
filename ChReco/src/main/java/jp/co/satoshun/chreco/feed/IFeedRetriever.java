package jp.co.satoshun.chreco.feed;


import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import java.util.List;

public interface IFeedRetriever {
    void feedCallback(List<SyndEntry> entryList);
}
