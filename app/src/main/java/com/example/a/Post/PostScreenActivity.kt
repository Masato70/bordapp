package com.example.a.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a.Home.HomeFragment
import com.example.a.MainActivity
import com.example.a.databinding.ActivityPostScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPostScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btncancel.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }

    private fun Adddata() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user!!.uid

        val docData: HashMap<String, Number> = hashMapOf(
            "numberExample" to 3.14159265
        )

        val nestedData: HashMap<String, String> = hashMapOf(
            "Title" to binding.etTitle.text.toString(),
            "details" to binding.etdetails.text.toString()
        )

        docData["objectExample"] = nestedData

        db.collection("users").document(uid)
            .collection("post").document("aa")
            .set(nestedData)
            .addOnSuccessListener {  }
    }

}