package shanghai.lzybetter.moblielaboratory.Class;

import java.util.HashMap;

/**
 * Created by lzy17 on 2017/4/21.
 */

public class SaveExperiment {

    private String experimentName;
    private HashMap<Integer,Boolean> isSelected;

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }
}
