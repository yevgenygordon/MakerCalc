package com.example.makercalc.adapter

import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.ui.TopicsFragmentDirections


/**
 * Der Item Adapter weist den views im ViewHolder den Inhalt zu
 */
class TopicDetailadapter(

    private val topicDetailViewModel: MainViewModel,

   private var dataset:List<String>

    ) : RecyclerView.Adapter<TopicDetailadapter.ItemViewHolder>() {







    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //  findViewById holen   -----------------
     val themeName: TextView = itemView.findViewById(R.id.templateListe_TV)


    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut


        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.topicsdetails_item,parent,false)

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
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val topicDetailItem: String = dataset[position]
        //   notifyDataSetChanged()


        holder.themeName.text = topicDetailItem



      // Btn  Themeauswahl

        holder.themeName.setOnClickListener {

            if (topicDetailItem == "3D Druck" ||
                topicDetailItem == "Bildhauerei" ||
                topicDetailItem == "Bild mahlen"
                )
            {
                topicDetailViewModel.getTemplateCalc()
                holder.itemView.findNavController()
                    .navigate(TopicsFragmentDirections.actionTopicsFragmentToConstructionkitFragment(

                        topicDetailItem
                    ))

            }
            if (topicDetailItem == "Fliesen legen" ||
                topicDetailItem == "Renovieren" ||
                topicDetailItem == "Tapezieren" ||
                topicDetailItem == "Computerreparatur" ||
                topicDetailItem == "Soft Installation" ||
                topicDetailItem == "Geräte Installation" ||
                topicDetailItem == "TV / Internet" ||
                topicDetailItem == "Grafikarbeiten")
            {
                topicDetailViewModel.getTemplateCalc()
                holder.itemView.findNavController()
                    .navigate(TopicsFragmentDirections.actionTopicsFragmentToListkitFragment(

                        topicDetailItem
                    ))

            }




        }












    }
}
