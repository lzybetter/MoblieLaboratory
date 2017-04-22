package shanghai.lzybetter.moblielaboratory.Class;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lzy17 on 2017/4/21.
 */

public class SaveExperimentItem {

    private String saveExpName;
    private List<String> selectedSensor;

    public SaveExperimentItem(String saveExpName, List<String> selectedSensor) {
        this.saveExpName = saveExpName;
        this.selectedSensor = selectedSensor;
    }

    public String getSaveExpName() {
        return saveExpName;
    }

    public List<String> getSelectedSensor() {
        return selectedSensor;
    }
}
