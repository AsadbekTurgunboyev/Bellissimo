package com.example.bellissimo.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bellissimo.*
import com.example.bellissimo.`interface`.BottomInterface
import com.example.bellissimo.models.BottomItemModel


class BottomAdapter(val list: List<BottomItemModel>, val bottomInterface: BottomInterface): RecyclerView.Adapter<BottomAdapter.BottomViewHolder>() {

    private var selectedPosition = 0

    inner class BottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)

        fun bind(bottomItemModel: BottomItemModel, position: Int) {
            if (selectedPosition == position){
                title.setTextColor(ColorStateList.valueOf(itemView.context.getColor(R.color.red)))
                image.imageTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.red))
            }else{
                title.setTextColor(ColorStateList.valueOf(itemView.context.getColor(R.color.unselected_text)))
                image.imageTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.unseletected_image))
            }

            itemView.setOnClickListener {
                val previousSelected: Int = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)

                bottomInterface.clickBottom(bottomItemModel.title)
            }
           title.text = bottomItemModel.title
            image.setImageResource(bottomItemModel.image)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bottom,parent,false)
        return BottomViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {
      holder.bind(list[position], position)
    }
}