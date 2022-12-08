package com.example.makercalc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.data.model.Themen


/**
 * Der Item Adapter weist den views im ViewHolder den Inhalt zu
 */
class Topicadapter(

    private val topicViewModel: MainViewModel,


    ) : RecyclerView.Adapter<Topicadapter.ItemViewHolder>() {



    private var topicdataset: List<Themen> = listOf()

    fun submitTopicList(list: List<Themen>){
        topicdataset = list
        topicdataset.sortedBy { it.themeID}
        notifyDataSetChanged()
    }

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO findViewById holen   -----------------


        val themeName: TextView = itemView.findViewById(R.id.themeName_TV)

        val recyclerView: RecyclerView = itemView.findViewById(R.id.topicDetailRecyclerView)


    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        // TODO Schreibe hier deinen Code -------------------

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.topics_item,parent,false)


        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)


    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */


    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return topicdataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val topicItem: Themen = topicdataset[position]
        //   notifyDataSetChanged()



            holder.themeName.text = topicItem.themeID.toString()


         val adapter = TopicDetailadapter(topicViewModel,topicItem.themeTemplate.toList())

         holder.recyclerView.adapter = adapter
    }
}
