package com.example.a.Profile

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.a.Profile.ProfCreateViewModel
import com.example.a.R
import com.example.a.databinding.ProfCreateFragmentBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ProfCreateFragment : Fragment() {

    companion object {
        fun newInstance() = ProfCreateFragment()
    }

    private lateinit var viewModel: ProfCreateViewModel
    private var _binding: ProfCreateFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btngallery.setOnClickListener {
            selectPhoto()
        }

        binding.btnkeep.setOnClickListener {
            profileiconupdate()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfCreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        binding.icon.setImageURI(it)
    }

    private fun selectPhoto() {
        launcher.launch(arrayOf("image/*"))
    }

    private fun profileiconupdate() {
        val icon = binding.icon.toString()
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val name = binding.etname.text.toString()
        val age = binding.etage.text.toString()
        val prof = binding.etmyprof.text.toString()

        val profileu_pdates = userProfileChangeRequest {
            photoUri = Uri.parse(icon)
        }


//        val storage = Firebase.storage
//        val file = Uri.fromFile(File.createTempFile())


        if (name.isNotEmpty() && age.isNotEmpty() && prof.isNotEmpty()) {

            user?.let {
                val uid = user.uid

                val update = hashMapOf(
                    "name" to name,
                    "age" to age,
                    "profile" to prof
                )

                db.collection("users").document(uid)
                    .set(update)
                    .addOnSuccessListener {
                        Log.d(TAG, "プロフィールを保存しました。")
                        Firebase.auth.signOut()
                        findNavController().navigate(R.id.action_prof_createFragment_to_loginFragment)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "エラーです。プロフィールを保存できていません。", e)
                    }
            }
        }
    }
}