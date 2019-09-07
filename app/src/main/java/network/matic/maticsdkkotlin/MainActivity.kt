package network.matic.maticsdkkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import network.matic.maticsdkkotlin.TestMaticK

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        TestMaticK.initWeb3()
    }
}
