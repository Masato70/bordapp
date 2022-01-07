package com.example.a.Security.Login

import android.content.ContentValues.TAG
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.a.R
import com.example.a.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            login()
        }

        binding.forgotpassword.setOnClickListener {
            pw_forget()
        }

        binding.btnregister.setOnClickListener {
            signin()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun login() {

        var email = binding.email.text.toString()
        var password = binding.password.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(context, "ログインしました。", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "ログインに成功しました。")
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, "メールアドレスかパスワードが間違えています。", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun signin() {
        findNavController().navigate(R.id.action_loginFragment_to_signinFragment)
    }

    private fun pw_forget() {
        findNavController().navigate(R.id.action_loginFragment_to_passwordFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}