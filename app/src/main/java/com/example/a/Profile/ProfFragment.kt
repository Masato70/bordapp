package com.example.a.Profile

import android.content.ContentValues.TAG
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
import com.example.a.LoginDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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

        //プロフィール取得(名前、年齢、自己紹介)
        val db = Firebase.firestore
        val uid = user?.uid
        val docRef = db.collection("users").document(uid.toString())

        //ログインしていたらプロフィール取得する
        if (user != null) {
            docRef.get()
                .addOnSuccessListener { document ->

                    if (document != null) {
                        Log.d(TAG, "ドキュメントデータ: ${document.data}")
                        binding.tvname.text = document.data!!["name"].toString()
                        binding.tvage.text = document.data!!["age"].toString()
                        binding.tvprof.text = document.data!!["profile"].toString()
                    }
                }
        }
    }

    //ダイアログ表示
    private fun dialog() {
        val user = Firebase.auth.currentUser

        //ログインしていなかったらログインを促すダイアログ表示
        if (user == null) {
            Log.d(TAG, "ダイアログを表示しました。")
            LoginDialogFragment().show(parentFragmentManager, "loginDialog")
        } else {
            Log.d(TAG, "ログインされています。")
        }
    }

    //プロフィール変更ボタンを押した時の動作
    private fun profchange() {

        val user = Firebase.auth.currentUser
        //ログインしていなかったらログイン画面に繊維
        if (user == null) {
            Log.d(TAG, "ログインされていません")
            findNavController().navigate(R.id.action_profFragment_to_loginFragment)
        } else {
            Log.d(TAG, "ログインしています")
            findNavController().navigate(R.id.action_profFragment_to_prof_ChangeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


