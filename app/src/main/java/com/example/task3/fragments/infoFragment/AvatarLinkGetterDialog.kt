package com.example.task3.fragments.infoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.task3.MainActivity
import com.example.task3.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.avatar_link_getter.*
import java.io.File

class AvatarLinkGetterDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.avatar_link_getter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navView: NavigationView? = activity?.findViewById(R.id.nav_view)
        val userImage = navView!!.getHeaderView(0).findViewById<ImageView>(R.id.avatar)
        userImage.setBackgroundColor(0)
        download_link_button.setOnClickListener {
            val str = edit_link.text.toString()
            Glide
                .with(context as MainActivity)
                .load(str)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .dontAnimate()
                .into(userImage)
            dismiss()
        }
  




    }
}
