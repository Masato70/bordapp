package com.example.a

import android.content.ContentValues
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Prof_ChangeFragment : Fragment() {

    companion object {
        fun newInstance() = Prof_ChangeFragment()
    }

    private lateinit var viewModel: ProfChangeViewModel
    private var _binding: ProfChangeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfChangeFragmentBinding.inflate(inflater, container, false)
        return binding.root    }

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

    private val launcher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        binding.icon.setImageURI(it)
    }

    private fun selectPhoto() {
        launcher.launch(arrayOf("image/*"))
    }


    private fun profChoice() {
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        user?.let {
            val uid = user.uid
            val docRef = db.collection("users").document(uid)

            docRef.get()
                .addOnSuccessListener { document->
                    if(document != null) {
                        profupdate()
                    } else if(document == null) {
                        profcreate()
                    }
                }

        }

    }


    private fun profcreate() {
        val icon = binding.icon.toString()
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val name = binding.etname.text.toString()
        val age = binding.etage.text.toString()
        val prof = binding.etmyprof.text.toString()

        val profileu_pdates = userProfileChangeRequest {
            photoUri = Uri.parse(icon)
        }

        user!!.updateProfile(profileu_pdates)
            .addOnCompleteListener { tast ->
                if (tast.isSuccessful) {
                    Log.d(ContentValues.TAG, "アイコン更新")
                }
            }

        user?.let {
            val uid = user.uid

            val create = hashMapOf(
                "name" to name,
                "age" to age,
                "profile" to prof
            )

            db.collection("users").document(uid)
                .set(create)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "プロフィールを保存しました。")
                    findNavController().navigate(R.id.action_prof_ChangeFragment_to_profFragment)
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "エラーです。プロフィールを保存できていません。", e) }
        }
    }

    private fun profupdate() {
        val icon = binding.icon.toString()
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val name = binding.etname.text.toString()
        val age = binding.etage.text.toString()
        val prof = binding.etmyprof.text.toString()

        val profileu_pdates = userProfileChangeRequest {
            photoUri = Uri.parse(icon)
        }

        user!!.updateProfile(profileu_pdates)
            .addOnCompleteListener { tast ->
                if (tast.isSuccessful) {
                    Log.d(ContentValues.TAG, "アイコン更新")
                }
            }
        user?.let {
            val uid = user.uid
            val prof_update = db.collection("users").document(uid)

            prof_update
                .update(mapOf(
                    "name" to name,
                    "age" to age,
                    "profile" to prof
                ))
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