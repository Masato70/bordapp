package com.example.a

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.a.Profile.ProfFragment
import com.example.a.Security.Login.LoginFragment
import com.example.a.Security.LoginActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlin.math.log

class loginDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("この機能を利用するには\nユーザー登録が必要です")
                .setItems(R.array.aiueo,
                    DialogInterface.OnClickListener { dialog, which ->

                        when(which) {
                            0 -> {
                                startActivity(Intent(context,LoginFragment::class.java))
                                Log.d(TAG, "登録する")
                            }
                            1 -> {
                                Log.d(TAG, "今はしない")
                            }
                        }

                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}