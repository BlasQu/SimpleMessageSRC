package com.example.simplemessage.feature.post

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.simplemessage.R
import com.example.simplemessage.databinding.FragmentPostBinding
import com.example.simplemessage.feature.messages.MessagesActivity
import com.example.simplemessage.feature.messages.MessagesViewModel
import com.example.simplemessage.util.Consts
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*
import java.util.jar.Manifest


class PostFragment: Fragment(R.layout.fragment_post) {

    lateinit var binding: FragmentPostBinding
    lateinit var messagesActivity: MessagesActivity
    private val viewmodel by sharedViewModel<MessagesViewModel>()
    private var editableMode = false
    private var imageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)
        messagesActivity = activity as MessagesActivity

        setupPost()
        setupButtons()
    }

    private fun pickImage(){
        val getImage = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(getImage, Consts.image_rq)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Consts.image_rq && resultCode == RESULT_OK) {
            imageUri = data?.data
            binding.imgPicture.setImageURI(imageUri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkPermission(permission: String, code: Int){
            when {
                shouldShowRequestPermissionRationale(permission) -> {
                    val builder = AlertDialog.Builder(messagesActivity)
                    builder.apply {
                        setTitle("Uprawnienia aplikacji")
                        setMessage("Jeżeli nie zaakceptujesz uprawnień, nie będziesz mógł zmieniać zdjęć w postach.")
                        setPositiveButton("Ok") { _, _ ->
                            ActivityCompat.requestPermissions(messagesActivity, arrayOf(permission), code)
                        }
                        setNeutralButton("Cancel") { _, _ ->

                        }
                    }
                }
                ContextCompat.checkSelfPermission(messagesActivity, permission) != PackageManager.PERMISSION_GRANTED -> {
                    ActivityCompat.requestPermissions(messagesActivity, arrayOf(permission), code)
                }
                !shouldShowRequestPermissionRationale(permission) -> {
                    messagesActivity.snackbarMessage("Nie udostępniłeś uprawnień do aplikacji. Możesz je zmienić w ustawieniach aplikacji.")
                }
                else -> ActivityCompat.requestPermissions(messagesActivity, arrayOf(permission), code)
            }
    }

    private fun setupButtons() {
        val keyboard: InputMethodManager = messagesActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.apply {
            binding.btnDelete.setOnClickListener {
                viewmodel.deletePost()
                messagesActivity.changeFragment()
            }

            binding.imgPicture.setOnClickListener {
                if (editableMode) {
                    if (ContextCompat.checkSelfPermission(messagesActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                        pickImage()
                    else checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, Consts.storage_permission_rq)
                    }
            }

            binding.btnEdit.setOnClickListener {
                if (!editableMode) {
                    editableMode = true
                    inputTitle.apply {
                        setText(textTitle.text)
                        visibility = View.VISIBLE
                        requestFocus()
                    }

                    inputDescription.apply {
                        setText(textDescription.text)
                        visibility = View.VISIBLE
                    }

                    textTitle.visibility = View.GONE
                    textDescription.visibility = View.GONE
                    btnEdit.text = resources.getString(R.string.update)

                    keyboard.showSoftInput(inputTitle, InputMethodManager.SHOW_IMPLICIT)
                }
                else if (inputTitle.text.isEmpty() || inputTitle.text.trim().length < 3)
                    inputTitle.error = resources.getString(R.string.title_error)
                else {
                    editableMode = false
                    textTitle.apply {
                        text = inputTitle.text.toString()
                        visibility = View.VISIBLE
                    }

                    textDescription.apply {
                        text = inputDescription.text.toString()
                        visibility = View.VISIBLE
                    }

                    inputTitle.visibility = View.GONE
                    inputDescription.visibility = View.GONE
                    btnEdit.text = resources.getString(R.string.edit)

                    viewmodel.updatePost(inputTitle.text.toString(), inputDescription.text.toString(), imageUri)
                    imageUri = null
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when {
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                pickImage()
            }
            !shouldShowRequestPermissionRationale(permissions[0]) -> {
                messagesActivity.snackbarMessage("Nie udostępniłeś uprawnień do aplikacji. Możesz je zmienić w ustawieniach aplikacji.")
            }
        }
    }

    private fun setupPost() {
        lifecycleScope.launch {
            viewmodel.currentPost.collect { post ->
                Log.d("debug", post.toString())
                binding.apply {
                    post?.let {
                        val title = post.title
                        val desc = post.description
                        val iconUri = post.icon

                        if (iconUri.isEmpty()) imgPicture.setActualImageResource(R.drawable.no_image_found)
                        else imgPicture.setImageURI(iconUri)
                        textTitle.text = title.capitalize(Locale.getDefault())
                        textDescription.apply {
                            text = desc.capitalize(Locale.getDefault())
                            movementMethod = ScrollingMovementMethod()
                        }
                    }
                }
            }
        }
    }
}