package com.badadarr.messengerapp.AdapterClasses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.badadarr.messengerapp.ModelClasses.Users
import com.badadarr.messengerapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_search_item_layout.view.*

class UserAdapter(
    private val mContext: Context,
    private val mUsers: List<Users>,
    private val isChatCheck: Boolean
) : RecyclerView.Adapter<UserAdapter.ViewHolder?>()  {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout, viewGroup, false)
        return UserAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val user: Users = mUsers[i]
        holder.userNameTxt.text = user.username

        if (user.profile.isNotEmpty()) Picasso.get().load(user.profile).placeholder(R.drawable.profile).into(holder.profileImageView)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userNameTxt: TextView = itemView.findViewById(R.id.username)
        var profileImageView: CircleImageView = itemView.findViewById(R.id.profile_image)
        var onlineImageView: CircleImageView = itemView.findViewById(R.id.image_online)
        var offlineImageView: CircleImageView = itemView.findViewById(R.id.image_offline)
        var lastMessageTxt: TextView = itemView.findViewById(R.id.message_last)

    }
}