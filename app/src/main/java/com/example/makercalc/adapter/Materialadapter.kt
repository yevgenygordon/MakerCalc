package com.example.makercalc.adapter




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.data.model.Materials
import com.example.makercalc.data.model.SingleCalc


/**
 * Der Item Adapter weist den views im ViewHolder den Inhalt zu
 */
class Materialadapter(

    private val materialViewModel: MainViewModel,


    ) : RecyclerView.Adapter<Materialadapter.ItemViewHolder>() {



    private var materialdataset: MutableList<Materials> = mutableListOf()

    fun submitMaterialsList(list: MutableList<Materials>){
        materialdataset = list
        materialdataset.sortedBy { it.materialName }
        notifyDataSetChanged()
    }

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO findViewById holen   -----------------


        val materialName: TextView = itemView.findViewById(R.id.materialName_TI)
        val materialWeight: TextView = itemView.findViewById(R.id.materialWeight_TI)
        val materialQuantity: TextView = itemView.findViewById(R.id.materialQuantity_TI)
        val materialPrice: TextView = itemView.findViewById(R.id.materialPrice_TI)


        val btn_minusMaterial: ImageView = itemView.findViewById(R.id.btn_minusMaterial)
        val btn_saveMaterial: ImageView = itemView.findViewById(R.id.btn_saveMaterial)


    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        // TODO Schreibe hier deinen Code -------------------

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.material_item,parent,false)

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
        return materialdataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val materialItem: Materials = materialdataset[position]
        var editMaterial:Materials
    //    notifyDataSetChanged()


        holder.materialName.text = materialItem.materialName
        holder.materialWeight.text = materialItem.materialweight.toString()
        holder.materialQuantity.text = materialItem.materialQuantity.toString()
        holder.materialPrice.text = materialItem.materialPriceItem.toString()




        holder.btn_minusMaterial.setOnClickListener {

            materialViewModel.deleteMaterial(materialItem)

        }

        holder.btn_saveMaterial.setOnClickListener {

          editMaterial = Materials(

                materialID = materialItem.materialID,
            materialName = holder.materialName.text.toString(),
            materialPriceItem = holder.materialPrice.text.toString().toDouble(),
            materialweight = holder.materialWeight.text.toString().toDouble(), // Gram
            materialQuantity = holder.materialQuantity.text.toString().toInt(),
              )
            materialViewModel.updateMaterial(editMaterial)
        }



    }
}
