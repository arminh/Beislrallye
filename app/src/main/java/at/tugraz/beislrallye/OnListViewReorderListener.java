package at.tugraz.beislrallye;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthias on 06.01.15.
 */
public interface OnListViewReorderListener {
    public <T> void onListViewReorder(List<T> content);
}
