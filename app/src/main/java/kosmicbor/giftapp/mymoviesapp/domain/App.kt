package kosmicbor.giftapp.mymoviesapp.domain

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import kosmicbor.giftapp.mymoviesapp.domain.room.HistoryDao
import kosmicbor.giftapp.mymoviesapp.domain.room.HistoryDataBase
import java.lang.Exception
import java.lang.IllegalStateException

class App : Application() {

    companion object {
        private var appInstance: App? = null
        private var dataBase: HistoryDataBase? = null
        private const val DATABASE_NAME = "HISTORY DATABASE"

        fun getHistoryDao(): HistoryDao {

            if (dataBase == null) {
                synchronized(HistoryDataBase::class.java) {

                    if (dataBase == null) {

                        if (appInstance == null)
                            throw IllegalStateException("Application is null while creating DataBase")

                        dataBase = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            HistoryDataBase::class.java,
                            DATABASE_NAME
                        )
                            //.allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return dataBase!!.historyDao()
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}
