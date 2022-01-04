package com.example.a.Security.Signin

import android.content.ContentValues.TAG
import android.content.Intent
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
import com.example.a.Security.Login.LoginViewModel
import com.example.a.databinding.LoginFragmentBinding
import com.example.a.databinding.SigninFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SigninFragment : Fragment() {

    companion object {
        fun newInstance() = SigninFragment()
    }

    private lateinit var viewModel: SigninViewModel
    private lateinit var auth: FirebaseAuth

    private var _binding: SigninFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SigninFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signin.setOnClickListener {
            signin()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SigninViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun signin() {
        var email = binding.email.text.toString()
        var password = binding.password.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { ctast ->
                if (ctast.isSuccessful) {
                    Log.d(TAG, "アカウントを作成しました。")
                    findNavController().navigate(R.id.action_signinFragment_to_loginFragment)
                }
            }
        } else if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context,"メールアドレスとパスワードを入力してください。",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}