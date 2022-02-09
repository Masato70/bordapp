package com.example.a

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.a.databinding.ProfChangeFragmentBinding
import com.example.a.databinding.ProfCreateFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.util.*

class Prof_ChangeFragment : Fragment() {

    companion object {
        fun newInstance() = Prof_ChangeFragment()
    }

    private lateinit var viewModel: ProfChangeViewModel
    private var _binding: ProfChangeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val RegisterTheIcon =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            binding.icon.setImageURI(it)
        }
    lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        storage = Firebase.storage
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfChangeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnkeep.setOnClickListener {
            profChoice()
        }

        binding.btngallery.setOnClickListener {
            selectPhoto()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfChangeViewModel::class.java)
        // TODO: Use the ViewModel
    }


    private fun selectPhoto() {
        RegisterTheIcon.launch(arrayOf("image/*"))
    }


    private fun profChoice() {
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        user?.let {
            val uid = user.uid
            val docRef = db.collection("users").document(uid)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        profupdate()
                    } else {
                        profcreate()
                    }
                }
        }
    }

    private fun profcreate() {
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val storageRef = storage.reference
        val name = binding.etname.text.toString()
        val age = binding.etage.text.toString()
        val prof = binding.etmyprof.text.toString()

//        val file = Uri.fromFile(File("path/to/images/rivers.jpg"))
//        val riversRef = storageRef.child("images/${binding.icon}")
//        riversRef.putFile(file)

//            .addOnSuccessListener {
//                Log.d(TAG, "アイコンを保存しました。")
//            }
//
//            .addOnFailureListener {
//                Log.d(TAG, "アイコン保存に失敗しました。")
//            }




        user?.let {
            val uid = user.uid

            val create = hashMapOf(
                "name" to name,
                "age" to age,
                "profile" to prof
            )

            if (name.isNotEmpty() && age.isNotEmpty() && prof.isNotEmpty()) {
                db.collection("users").document(uid)
                    .set(create)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "プロフィールを保存しました。")
                        findNavController().navigate(R.id.action_prof_ChangeFragment_to_profFragment)
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            ContentValues.TAG,
                            "エラーです。プロフィールを保存できていません。", e
                        )
                    }
            }
        }
    }

    private fun profupdate() {
        val icon = binding.icon.toString()
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val storageRef = storage.reference
        val name = binding.etname.text.toString()
        val age = binding.etage.text.toString()
        val prof = binding.etmyprof.text.toString()


//        val fileName = UUID.randomUUID().toString() + ".jpg"
//        val riversRef = FirebaseStorage.getInstance().reference.child("/images/$fileName")
//
//
//
//        riversRef.putFile("${binding.icon})
//            .addOnSuccessListener {
//                Log.d(TAG, "アイコンを保存しました。")
//            }



        user?.let {
            val uid = user.uid
            val prof_update = db.collection("users").document(uid)

            prof_update
                .update(
                    mapOf(
                        "name" to name,
                        "age" to age,
                        "profile" to prof
                    )
                )

                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_prof_ChangeFragment_to_profFragment)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}