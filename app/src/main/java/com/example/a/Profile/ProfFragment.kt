package com.example.a.Profile

import android.content.ContentValues.TAG
import android.content.Context
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
import com.google.protobuf.Empty
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
        Log.d(TAG, "ああ。")

        dialog()
        profshow()

        binding.btnchange.setOnClickListener {
            profchange()

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfViewModel::class.java)
        // TODO: Use the ViewModel
    }

    //データ読み込み
    private fun profshow() {
        val user = Firebase.auth.currentUser

        //アイコン取得
        user?.let {
            for (profile in it.providerData) {
                val photoUrl = profile.photoUrl
                binding.icon.setImageURI(photoUrl)
            }


            val uid = user.uid
            val db = Firebase.firestore
            //プロフィール取得(名前、年齢、自己紹介)
            val docRef = db.collection("users").document(uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        binding.tvname.text = document.data!!["name"].toString()
                        binding.tvage.text = document.data!!["age"].toString()
                        binding.tvprof.text = document.data!!["profile"].toString()
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }
    }

    //ダイアログ表示
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

    //プロフィール変更ボタンを押した時の動作
    private fun profchange() {

        //ログインしていなかったらログイン画面に繊維
        if (auth != null) {
            findNavController().navigate(R.id.action_profFragment_to_prof_ChangeFragment)
        } else {
            findNavController().navigate(R.id.action_profFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


