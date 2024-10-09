package com.huy.appnoithat.Service.FileNoiThatExplorer;

import com.huy.appnoithat.DataModel.RecentFile;
import javafx.collections.ObservableList;

public interface FileNoiThatExplorerService {
    ObservableList<RecentFile> getRecentFile();

    void saveRecentFile();

    void addRecentFile(RecentFile recentFile);

    void removeRecentFile(RecentFile recentFile);
}
