package fr.istic.mob.star2dp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.istic.mob.star2dp.fragments.Fragment1
import fr.istic.mob.star2dp.util.Intermediate
import fr.istic.mob.star2dp.util.Utils

class MainActivity : AppCompatActivity(), Intermediate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.setContext(this)

        val fragment1 = Fragment1()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment1)
            .commit()
    }

    override fun sendData(receiver: Fragment, data: HashMap<String, Any>) {

        val bundle = Bundle()
        bundle.putSerializable("data", data)
        receiver.arguments = bundle

        this.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
            )
            .replace(R.id.fragment_container, receiver)
            .commit()
    }
}