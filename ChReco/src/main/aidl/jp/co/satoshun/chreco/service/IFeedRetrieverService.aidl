package jp.co.satoshun.chreco.service;


interface IFeedRetrieverService {
    void retriveSyndEntryList(in List<String> feedUrlList);
    void renewSyndEntryList();
}
