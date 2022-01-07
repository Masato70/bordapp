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
import com.example.a.databinding.LoginFragmentBinding
import com.example.a.databinding.ProfCreateFragmentBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class ProfCreateFragment : Fragment() {

    companion object {
        fun newInstance() = ProfCreateFragment()
    }

    private lateinit var viewModel: ProfCreateViewModel
    private var _binding: ProfCreateFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


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
            profileupdates()
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

    private fun profileupdates() {
        val user = Firebase.auth.currentUser
        val name = binding.etname.text.toString()
        val icon = binding.icon.toString()

        val profileu_pdates = userProfileChangeRequest {
            displayName = name
            photoUri = Uri.parse(icon)
        }

        user!!.updateProfile(profileu_pdates)
            .addOnCompleteListener { tast->
                if(tast.isSuccessful) {
                    Log.d(TAG, "プロフィール更新")
                    findNavController().navigate(R.id.action_profFragment_to_loginFragment)
                }
            }
    }

}