package tk.skeptick.skyeng_dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import tk.skeptick.skyeng_dictionary.databinding.ActivityMainBinding
import tk.skeptick.skyeng_dictionary.ui.main.MainFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(binding.fragmentContainer.id, MainFragment())
            }
        }
    }

}