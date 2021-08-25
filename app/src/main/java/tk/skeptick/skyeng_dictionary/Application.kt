package tk.skeptick.skyeng_dictionary

import io.realm.Realm

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}