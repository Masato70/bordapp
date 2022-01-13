package com.example.a

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.a.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView
            .setupWithNavController(navController)


    }

    private fun Screenselection() {

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val authuid = user!!.uid

        val db = Firebase.firestore
        var documentID = db.collection("users").document(authuid)
        documentID.get()

        //ユーザーUIDとドキュメントIDが同じものがある場合
        //ホーム画面に遷移

        //ユーザーUIDとドキュメントIDが同じものがない場合
        //同じにする
        //プロフィール設定画面に遷移

    }

}