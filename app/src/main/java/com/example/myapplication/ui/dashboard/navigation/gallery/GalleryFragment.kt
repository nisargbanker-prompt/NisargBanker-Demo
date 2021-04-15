package com.example.myapplication.ui.dashboard.navigation.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            SafetyNet.getClient(this@GalleryFragment.requireActivity()).verifyWithRecaptcha("6Lc0rqYaAAAAANHGQ-GyvSkodZfqTIqCW9tOZ4Cr")
                .addOnSuccessListener { recaptchaTokenResponse ->
                    // Indicates communication with reCAPTCHA service was
                    // successful.
                    val userResponseToken =
                        recaptchaTokenResponse.tokenResult
                    if (!userResponseToken.isEmpty()) {
                        // Validate the user response token using the
                        // reCAPTCHA siteverify API.
                        Log.e("TAG", "VALIDATION STEP NEEDED")
                    }
                }
                .addOnFailureListener { e ->
                    if (e is ApiException) {
                        // An error occurred when communicating with the
                        // reCAPTCHA service. Refer to the status code to
                        // handle the error appropriately.
                        val apiException =
                            e as ApiException
                        val statusCode = apiException.statusCode
                        Log.e(
                            "TAG", "Error: " + CommonStatusCodes
                                .getStatusCodeString(statusCode)
                        )
                    } else {
                        // A different, unknown type of error occurred.
                        Log.e("TAG", "Error: " + e.message)
                    }
                }
        }


    }
}