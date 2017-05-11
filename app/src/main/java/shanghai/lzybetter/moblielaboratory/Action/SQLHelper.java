package shanghai.lzybetter.moblielaboratory.Action;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by lzy17 on 2017/5/11.
 */

public class SQLHelper extends SQLiteOpenHelper {

    private static SQLHelper sqlHelper;
    private static SQLiteDatabase sqLiteDatabase;

    private SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getInstance(Context context,String db_name){
        if(sqlHelper == null){
            File path = null;
            try {
                path = new File(Environment.getExternalStorageDirectory().getCanonicalPath()
                        + File.separator + "SensorData");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!path.exists()){
                path.mkdir();
            }
            try {
                File targetFile = new File (path.getCanonicalPath() + File.separator + db_name);
                if(!targetFile.exists()){
                    targetFile.createNewFile();
                }
                sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(targetFile,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sqLiteDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
