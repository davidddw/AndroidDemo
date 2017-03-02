package org.cloud.carassistant.di.modules;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.cloud.carassistant.BuildConfig;
import org.cloud.carassistant.db.DbOpenHelper;
import org.cloud.carassistant.db.dao.ConsumerDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */
@Module
public class DbModule {

    @Provides
    @Singleton
    SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DbOpenHelper(application);
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        SqlBrite.Builder builder = new SqlBrite.Builder();
        return builder.build();
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(BuildConfig.DEBUG);
        return db;
    }

    @Provides
    @Singleton
    ConsumerDao provideConsumerDao(BriteDatabase briteDatabase) {
        return new ConsumerDao(briteDatabase);
    }


}
