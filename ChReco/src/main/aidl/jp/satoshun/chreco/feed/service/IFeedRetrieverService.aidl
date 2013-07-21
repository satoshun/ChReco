package jp.satoshun.chreco.feed.service;

import jp.satoshun.chreco.service.IFeedObserver;

interface IFeedRetrieverService {
    void retriveSyndEntryList(in List<String> feedUrlList);
    void setObserver(IFeedObserver target);
}
