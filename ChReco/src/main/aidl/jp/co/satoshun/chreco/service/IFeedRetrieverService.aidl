package jp.co.satoshun.chreco.service;


interface IFeedRetrieverService {
    List<SyndEntry> getSyndEntryList();
    void renewSyndEntryList();
}
