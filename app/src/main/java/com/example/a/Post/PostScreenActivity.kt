package com.example.a.Post

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        binding.btnpost.setOnClickListener { Adddata() }
    }

    private fun Adddata() {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user!!.uid

        val docData: HashMap<String, Any> = hashMapOf(
            "numberExample" to 3.14159265
        )

        val nestedData: HashMap<String, Any> = hashMapOf(
            "Title" to binding.etTitle.text.toString(),
            "details" to binding.etdetails.text.toString()
        )

        docData["objectExample"] = nestedData

        db.collection("users").document(uid)
            .collection("user posts").document("Post")
            .set(nestedData)
            .addOnSuccessListener {
                Log.d(TAG, "データ保存")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d(TAG, "データ保存失敗")
                Toast.makeText(this, "失敗", Toast.LENGTH_SHORT).show()
            }
    }

}