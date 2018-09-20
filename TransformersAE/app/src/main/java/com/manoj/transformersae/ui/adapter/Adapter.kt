package com.manoj.transformersae.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.manoj.transformersae.R
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.ItemDetailActivity
import com.manoj.transformersae.ui.ItemDetailFragment
import com.manoj.transformersae.ui.ItemListActivity
import kotlinx.android.synthetic.main.item_list_content.view.*

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class Adapter (val parentActivity: ItemListActivity, private val values: List<BotModel>, private val twoPane: Boolean)
    : RecyclerView.Adapter<Adapter.ViewHolder>() {

//    private val onClickListener: View.OnClickListener

    init {
        /*
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyContent.DummyItem
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameTextView.text = item.name
        holder.strengthTextView.text = item.strength.toString()
        holder.intelligenceTextView.text = item.intelligence.toString()
        holder.speedTextView.text = item.speed.toString()
        holder.enduranceTextView.text = item.endurance.toString()
        holder.rankTextView.text = item.rank.toString()
        holder.courageTextView.text = item.courage.toString()
        holder.firepowerTextView.text = item.firepower.toString()
        holder.skillTextView.text = item.skill.toString()
        holder.teamTextView.text = item.team.toString()

        with(holder.itemView) {
            tag = item
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconImageView:ImageView = view.image
        val iconEdit:ImageView = view.edit
        val iconDelete:ImageView = view.delete
        val nameTextView: TextView = view.name
        val strengthTextView: TextView = view.strength
        val intelligenceTextView: TextView = view.strength
        val speedTextView: TextView = view.strength
        val enduranceTextView: TextView = view.strength
        val rankTextView: TextView = view.strength
        val courageTextView: TextView = view.strength
        val firepowerTextView: TextView = view.strength
        val skillTextView: TextView = view.strength
        val teamTextView: TextView = view.team
    }
}