package com.example.simplemessage.feature.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.simplemessage.R
import com.example.simplemessage.databinding.FragmentPostBinding

class PostFragment: Fragment(R.layout.fragment_post) {

    lateinit var binding: FragmentPostBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)
    }
}