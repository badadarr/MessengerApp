package com.badadarr.messengerapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badadarr.messengerapp.ModelClasses.Users
import com.badadarr.messengerapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_setting.view.*


class SettingFragment : Fragment() {

    private var userReference : DatabaseReference? = null
    private var firebaseUser : FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        userReference = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        userReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user : Users? = p0.getValue(Users::class.java)

                    if (context!=null) {
                        view.username_settings.text = user!!.username

                        if (user.profile.isNotEmpty())
                            Picasso.get().load(user.profile).into(view.profile_image_settings)

                        if (user.cover.isNotEmpty())
                            Picasso.get().load(user.cover).into(view.cover_image_settings)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        return view
    }
}