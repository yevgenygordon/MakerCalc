package com.example.makercalc.adapter




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.data.model.Detail
import com.example.makercalc.data.model.ListenCalc
import com.example.makercalc.data.model.Materials



/**
 * Der Item Adapter weist den views im ViewHolder den Inhalt zu
 */
class Listkitadapter(

    private val listkitViewModel: MainViewModel,


    ) : RecyclerView.Adapter<Listkitadapter.ItemViewHolder>() {



    private var listkitdataset: MutableList<Detail> = mutableListOf()

    fun submitListencalcList(list: MutableList<Detail>){
        listkitdataset = list
        listkitdataset.sortedBy { it.detailTitle }
        notifyDataSetChanged()
    }

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO findViewById holen   -----------------


        val title: TextView = itemView.findViewById(R.id.titel_TI)
        val quantity: TextView = itemView.findViewById(R.id.menge_TI)
        val price: TextView = itemView.findViewById(R.id.unitPrice_TI)



        val btn_minusRegard: ImageView = itemView.findViewById(R.id.btn_minus_Regard)


    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        // TODO Schreibe hier deinen Code -------------------

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.calc2_item,parent,false)

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
        return listkitdataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val listkitItem: Detail = listkitdataset[position]
     //   notifyDataSetChanged()


        holder.title.text = listkitItem.detailTitle
        holder.quantity.text = listkitItem.detailQuantity
        holder.price.text = listkitItem.detailPrice


         holder.btn_minusRegard.setOnClickListener {


        }





    }
}
