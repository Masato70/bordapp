package com.example.a.Home

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a.Post.CustomAdapter
import com.example.a.Post.Post
import com.example.a.Post.PostScreenActivity
import com.example.a.databinding.HomeFragmentBinding
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnpostsc.setOnClickListener {
            startActivity(
                Intent(context, PostScreenActivity::class.java)
            )
        }

        val customAdapter = CustomAdapter()
        binding.PostDisplay.adapter = customAdapter
        binding.PostDisplay.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val uid = user!!.uid

        val citiesRef = db.collectionGroup("UserPosts")
        citiesRef.orderBy("CreateTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { Log.d(TAG, "読み取り成功") }
            .addOnFailureListener { Log.d(TAG, "読み取り失敗") }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}