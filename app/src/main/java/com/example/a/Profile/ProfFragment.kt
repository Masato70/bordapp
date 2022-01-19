package com.example.a.Profile

import android.content.ContentValues.TAG
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.a.R
import com.example.a.databinding.ProfFragmentBinding
import com.example.a.loginDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

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
        dialog()

        binding.btnchange.setOnClickListener {
            if (auth == null) {
                findNavController().navigate(R.id.action_profFragment_to_loginFragment)
            } else if (auth != null) {
                profchange()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun dialog() {
        val user = Firebase.auth.currentUser

        //ログインしていなかったらログインを促すダイアログ表示
        if (user == null) {
            Log.d(TAG, "ダイアログを表示しました。")
            loginDialogFragment().show(parentFragmentManager, "loginDialog")
        } else {
            Log.d(TAG, "ログインされています。")
        }
    }

    private fun profchange() {
        val user = Firebase.auth.currentUser

        user?.let {
            val uid = user.uid
            val db = Firebase.firestore

            //アイコン取得
            val photoUrl = user.photoUrl
            binding.icon.setImageURI(photoUrl)

            //プロフィール取得(名前、年齢、自己紹介)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

