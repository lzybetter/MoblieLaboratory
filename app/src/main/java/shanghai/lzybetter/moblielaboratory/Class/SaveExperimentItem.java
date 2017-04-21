package shanghai.lzybetter.moblielaboratory.Class;

import java.util.HashMap;

/**
 * Created by lzy17 on 2017/4/21.
 */

public class SaveExperimentItem {

    private String saveExpName;
    private HashMap<Integer,Boolean> isSelected;

    public SaveExperimentItem(String saveExpName, HashMap<Integer, Boolean> isSelected) {
        this.saveExpName = saveExpName;
        this.isSelected = isSelected;
    }

    public String getSaveExpName() {
        return saveExpName;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
}
