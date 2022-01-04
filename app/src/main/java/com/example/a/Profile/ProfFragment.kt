package com.example.a.Profile

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a.databinding.ProfFragmentBinding
import com.example.a.loginDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfFragment : Fragment() {

    companion object {
        fun newInstance() = ProfFragment()
    }

    private lateinit var viewModel: ProfViewModel
    private var _binding: ProfFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfFragmentBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = Firebase.auth.currentUser

        if(user == null) {
            Log.d(TAG, "ああああ")
            loginDialogFragment().show(parentFragmentManager,"AAA")
        }else if(user != null) {
            Log.d(TAG,"え？")
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfViewModel::class.java)
        // TODO: Use the ViewModel
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}