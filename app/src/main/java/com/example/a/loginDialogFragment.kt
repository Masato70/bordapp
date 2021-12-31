package com.example.a

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.a.Security.LoginActivity
import kotlin.math.log

class loginDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("ああああああ")
                .setItems(R.array.aiueo,
                    DialogInterface.OnClickListener { dialog, which ->

                        when(which) {
                            0 -> {
                                Log.d(TAG, "ああ")
                            }
                            1 -> {
                                Log.d(TAG, "いい")
                            }
                        }

                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}