package com.example.a.Security.pswword

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.a.R
import com.example.a.databinding.PasswordFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordFragment : Fragment() {

    companion object {
        fun newInstance() = PasswordFragment()
    }

    private lateinit var viewModel: PasswordViewModel
    private lateinit var auth: FirebaseAuth

    private var _binding: PasswordFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnresetting.setOnClickListener {
            pwresetto()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun pwresetto() {
        val Address = binding.etresettingemail.text.toString()
        Firebase.auth.sendPasswordResetEmail(Address)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "メールを送信しました。")
                    Toast.makeText(context, "メールを送信しました。", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_passwordFragment_to_loginFragment)
                } else {
                    Toast.makeText(context, "メールを送信しました。", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_passwordFragment_to_loginFragment)

                }
            }
    }

}