package tk.skeptick.skyeng_dictionary

import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}