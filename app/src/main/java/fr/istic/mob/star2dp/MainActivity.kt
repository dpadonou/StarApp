package fr.istic.mob.star2dp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.istic.mob.star2dp.fragments.Fragment1
import fr.istic.mob.star2dp.util.Utils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.Companion.setContext(this)

        val fragment1 = Fragment1()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment1)
            .commit()

    }
}