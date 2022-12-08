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
import com.example.makercalc.data.model.Kunden



/**
 * Der Item Adapter weist den views im ViewHolder den Inhalt zu
 */
class Kundenadapter(

  private val viewModel: MainViewModel

   // private val delete : (Kunden) -> Unit,
    // private val save : (Kunden) -> Unit

    ) : RecyclerView.Adapter<Kundenadapter.ItemViewHolder>() {




    private var dataset =  listOf<Kunden>()
    fun submitList(list: List<Kunden>){
        dataset = list
        dataset.sortedBy { it.kundenName }
        notifyDataSetChanged()
    }

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO findViewById holen   -----------------

        val kundeName: TextView = itemView.findViewById(R.id.kundenName_TI)
        val kundeNameTV: TextView = itemView.findViewById(R.id.kundenName_tV)
        val kundeAddress: TextView = itemView.findViewById(R.id.kundenAdresse_TI)
        val kundeCity: TextView = itemView.findViewById(R.id.kundenCity_TI)
        val kundePostcode: TextView = itemView.findViewById(R.id.kundenPostcode_TI)


        val btn_Save: ImageView = itemView.findViewById(R.id.btn_Save)
        val btn_Delete: ImageView = itemView.findViewById(R.id.btn_deleteKunde)
        val kundenCardView: CardView = itemView.findViewById(R.id.kunden_cardView)
        val kundenDetail: ConstraintLayout = itemView.findViewById(R.id.kundenDetail)


    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        // TODO Schreibe hier deinen Code -------------------

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.kunden_item,parent,false)

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

        val kundeItem: Kunden = dataset[position]
        var editKunde: Kunden


        var isDetailViewOn: Boolean = false


        holder.kundeName.text = kundeItem.kundenName.toString()
        holder.kundeNameTV.text = kundeItem.kundenName.toString()
        holder.kundeAddress.text = kundeItem.kundenAdress.toString()
        holder.kundeCity.text = kundeItem.kundenCity.toString()
        holder.kundePostcode.text = kundeItem.kundenPostcode.toString()




        holder.kundenCardView.setOnClickListener {

          if (isDetailViewOn == false){
              holder.kundenDetail.visibility = View.VISIBLE
              isDetailViewOn = true
          }
            else {

                holder.kundenDetail.visibility = View.GONE
                isDetailViewOn = false

            }


            holder.btn_Save.setOnClickListener {

                 editKunde = Kunden(

                    kundenID = kundeItem.kundenID,
                    kundenName = holder.kundeName.text.toString(),
                    kundenEmail = "",
                    kundenImage = "",

                    kundenAdress = holder.kundeAddress.text.toString(),
                    kundenCity = holder.kundeCity.text.toString(),
                    kundenPostcode = holder.kundePostcode.text.toString(),
                    kundenTelNumber = "",

                    )

                viewModel.saveKunde(editKunde)

            }

            holder.btn_Delete.setOnClickListener {


                viewModel.delKunde(kundeItem)


                notifyDataSetChanged()


            }


        }

    }




}
