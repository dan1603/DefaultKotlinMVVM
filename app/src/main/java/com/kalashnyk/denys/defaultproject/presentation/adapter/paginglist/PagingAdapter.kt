package com.kalashnyk.denys.defaultproject.presentation.adapter.paginglist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.kalashnyk.denys.defaultproject.R
import com.kalashnyk.denys.defaultproject.utils.CARD_ARTICLE
import com.kalashnyk.denys.defaultproject.utils.CARD_EVENT
import com.kalashnyk.denys.defaultproject.utils.CARD_MESSAGES
import com.kalashnyk.denys.defaultproject.utils.CARD_USER
import com.kalashnyk.denys.defaultproject.utils.glide.GlideApp

/**
 * todo finished paging adapter
 */
class PagingAdapter(diffUtil: DiffUtil.ItemCallback<BaseCardModel>) :
    MultiTypeDataBoundAdapter<BaseCardModel, ViewDataBinding>(diffUtil) {

    override fun getItemLayoutId(position: Int): Int {
        getItem(position)?.let {
            return when (it.getCardType()) {
                CARD_USER -> R.layout.item_user
                CARD_MESSAGES -> R.layout.item_messages
                CARD_EVENT -> R.layout.item_event
                CARD_ARTICLE -> R.layout.item_article
                else -> -1
            }
        }
        return -1
    }

    override fun bindItem(holder: DataBoundViewHolder<ViewDataBinding>, position: Int, payloads: List<Any>) {
        super.bindItem(holder, position, payloads)

    }
}