package fr.istic.mob.star2dp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.istic.mob.star2dp.util.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.setContext(this)
    }

}