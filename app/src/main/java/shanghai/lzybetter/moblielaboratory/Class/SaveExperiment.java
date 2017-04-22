package shanghai.lzybetter.moblielaboratory.Class;

import org.litepal.crud.DataSupport;

import java.util.HashMap;

/**
 * Created by lzy17 on 2017/4/21.
 */

public class SaveExperiment extends DataSupport {

    private String experimentName;

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

}
