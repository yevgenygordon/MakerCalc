package com.example.makercalc.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.data.model.SingleCalc
import com.example.makercalc.ui.HomeFragmentDirections


/**
 * Der Item Adapter weist den views im ViewHolder den Inhalt zu
 */
class HomeCalcadapter(

    private val calcViewModel: MainViewModel,


    ) : RecyclerView.Adapter<HomeCalcadapter.ItemViewHolder>() {


    private var calcdataset: MutableList<SingleCalc> = mutableListOf()


    fun submitCalcList(list: MutableList<SingleCalc>) {
        calcdataset = list
        calcdataset.sortedBy { it.singleCalcName }
        notifyDataSetChanged()
    }

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    class ItemViewHolder(itemView: View, val lifecycleOwner: LifecycleOwner) :
        RecyclerView.ViewHolder(itemView) {
        // TODO findViewById holen   -----------------

        val themeImage: ImageView = itemView.findViewById(R.id.theme_iV)
        val favoriteImage: ImageView = itemView.findViewById(R.id.favorite_iV)
        val calcName: TextView = itemView.findViewById(R.id.calcName_tV)
        val calcDescription: TextView = itemView.findViewById(R.id.calcTextShort_tV)
        val calcDescriptionLong: TextView = itemView.findViewById(R.id.calcText_tV)
        val calcImage: ImageView = itemView.findViewById(R.id.calcImage_iV)
        val calc_cardView: CardView = itemView.findViewById(R.id.calc_cardView)
        val calcDetail: ConstraintLayout = itemView.findViewById(R.id.calcDetailBotom)

        val btn_favStar: ImageView = itemView.findViewById(R.id.btn_favStar)
        val btn_Send: ImageView = itemView.findViewById(R.id.btn_Send)
        val btn_Edit: ImageView = itemView.findViewById(R.id.btn_Edit)
        val btn_Copy: ImageView = itemView.findViewById(R.id.btn_Copy)
        val btn_deleteCalc: ImageView = itemView.findViewById(R.id.btn_deleteCalc)

        val productionCostOf1Part: TextView = itemView.findViewById(R.id.ProductionCostOf1Part_TV)
        val productionCostOf2Part: TextView = itemView.findViewById(R.id.ProductionCostOf2Part_TV)
        val costOfModeling: TextView = itemView.findViewById(R.id.costOfModeling_TV)
        val prototypeCost: TextView = itemView.findViewById(R.id.prototypeCost_TV)
        val postProcessingCost: TextView = itemView.findViewById(R.id.postProcessingCost_TV)
        val total: TextView = itemView.findViewById(R.id.total_TV)


    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val lifeCycleOwner = parent.context as LifecycleOwner

        // das itemLayout wird gebaut
        // TODO Schreibe hier deinen Code -------------------

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.calc_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout, lifeCycleOwner)


    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */


    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return calcdataset.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val calcItem: SingleCalc = calcdataset[position]



        calcViewModel.getMatDevCostOfProd1(calcItem.singleCalcID)
        calcViewModel.getMaterialCost(calcItem.singleCalcID)
        calcViewModel.getDeviceCost(calcItem.singleCalcID)

        var materialsCost: Double = 0.0
        var devicesCost: Double = 0.0
        var isDetailViewOn: Boolean = false
        var prototypeCost = 0.0
        var totalCost = 0.0



        calcViewModel.materialsCost.observe(
            holder.lifecycleOwner,
            Observer {
                materialsCost = it
                //   holder.productionCostOf1Part.text = materialsCost.toString()
            }
        )




        calcViewModel.devicesCost.observe(
            holder.lifecycleOwner,
            Observer {

                var postProCost = calcItem.postProcessingPrice * calcItem.quantity

                var prodCostOf1Part = (((it + materialsCost) * 3 * calcItem.difficultyFactor)
                        + calcItem.modelingCost + calcItem.workExtraPrice +
                        (calcItem.workPriceHour * calcItem.workTime))

                var prodCostOf2Part = prodCostOf1Part - calcItem.modelingCost

                if (calcItem.prototype == 1) {
                    prototypeCost = prodCostOf2Part * 0.75
                } else {
                    prototypeCost = calcItem.isFavorite.toDouble()
                }

                totalCost = ((prodCostOf2Part * calcItem.quantity) + prototypeCost
                        + postProCost + calcItem.modelingCost)


                holder.productionCostOf1Part.text =
                    "%.2f".format(prodCostOf1Part)
                        .toString() + " " + holder.itemView.context.getString(R.string.currency)
                holder.productionCostOf2Part.text =
                    "%.2f".format(prodCostOf2Part)
                        .toString() + " " + holder.itemView.context.getString(R.string.currency)
                holder.prototypeCost.text =
                    "%.2f".format(prototypeCost)
                        .toString() + " " + holder.itemView.context.getString(R.string.currency)
                holder.total.text =
                    "%.2f".format(totalCost)
                        .toString() + " " + holder.itemView.context.getString(R.string.currency)

                holder.costOfModeling.text =
                    "%.2f".format(calcItem.modelingCost)
                        .toString() + " " + holder.itemView.context.getString(R.string.currency)
                holder.postProcessingCost.text =
                    "%.2f".format(postProCost)
                        .toString() + " " + holder.itemView.context.getString(R.string.currency)

            }
        )




        holder.calcName.text = calcItem.singleCalcName
        holder.calcDescription.text = calcItem.singleCalcDescription
        holder.calcDescriptionLong.text = calcItem.singleCalcDescription



        when (calcItem.themeID){

            "3D Druck" -> {holder.themeImage.setImageResource(R.drawable.ddruck)}
            "Bildhauerei" -> {holder.themeImage.setImageResource(R.drawable.hammer)}
            "Bild mahlen" -> {holder.themeImage.setImageResource(R.drawable.illustrator)}

        }

        when (calcItem.isFavorite){
            1 -> { holder.favoriteImage.setImageResource(R.drawable.favoritestarlila)}
            0 -> { holder.favoriteImage.setImageResource(R.drawable.favoritestar)}

        }


        holder.themeImage
        holder.calcImage.load(calcItem.image)

        /*

        Log.d("HomecalCadapter", "${calcItem.singleCalcName} =  ${calcItem.isFavorite}")
        holder.favoriteImage.setImageResource(
            if (calcItem.isFavorite == 1) R.drawable.favoritestarlila
            else R.drawable.favoritestar
        )


         */


        holder.btn_Edit.setOnClickListener {

            calcViewModel.getCalc(calcItem.singleCalcID)
            holder.itemView.findNavController()
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToConstructionkitFragment(
                        calcItem.themeID,

                        )
                )


        }

        holder.btn_Copy.setOnClickListener {

            calcViewModel.copyCalc(calcItem)
        }

        holder.btn_Send.setOnClickListener { }


        holder.btn_deleteCalc.setOnClickListener {
            calcViewModel.deleteCalc(calcItem.singleCalcID)
            holder.calcDetail.visibility = View.GONE
            isDetailViewOn = false

        }

        holder.btn_favStar.setOnClickListener {

            if (calcItem.isFavorite == 1) {
                holder.favoriteImage.setImageResource(R.drawable.favoritestar)
                calcItem.isFavorite = 0
                calcViewModel.updateFavorite(calcItem.singleCalcID, calcItem.isFavorite)


            } else {
                holder.favoriteImage.setImageResource(R.drawable.favoritestarlila)
                calcItem.isFavorite = 1
                calcViewModel.updateFavorite(calcItem.singleCalcID, calcItem.isFavorite)

            }

        }


        holder.calc_cardView.setOnClickListener {


            if (isDetailViewOn == false) {
                calcViewModel.getCalc(calcItem.singleCalcID)
                holder.calcDetail.visibility = View.VISIBLE
                isDetailViewOn = true


            } else {
                holder.calcDetail.visibility = View.GONE
                isDetailViewOn = false
            }

        }


    }
}
