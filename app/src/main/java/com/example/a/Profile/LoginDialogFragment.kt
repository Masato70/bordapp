package com.example.a.Profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.a.R

class LoginDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("この機能を利用するには\nユーザー登録が必要です")
                .setItems(
                    R.array.aiueo,
                    DialogInterface.OnClickListener { _, which ->

                        when(which) {
                            0 -> {
                                findNavController().navigate(R.id.action_profFragment_to_loginFragment)
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