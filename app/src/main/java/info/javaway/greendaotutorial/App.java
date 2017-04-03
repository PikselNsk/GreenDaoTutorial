package info.javaway.greendaotutorial;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import info.javaway.greendaotutorial.model.DaoMaster;
import info.javaway.greendaotutorial.model.DaoSession;


public class App extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mycats-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
