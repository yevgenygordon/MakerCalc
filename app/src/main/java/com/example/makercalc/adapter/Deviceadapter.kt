package com.example.makercalc.adapter




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.makercalc.MainViewModel
import com.example.makercalc.R
import com.example.makercalc.data.model.Devices
import com.example.makercalc.data.model.Materials


/**
 * Der Item Adapter weist den views im ViewHolder den Inhalt zu
 */
class Deviceadapter(

    private val deviceViewModel: MainViewModel,


    ) : RecyclerView.Adapter<Deviceadapter.ItemViewHolder>() {



    private var devicedataset: MutableList<Devices> = mutableListOf()

    fun submitdeviceList(list: MutableList<Devices>){
        devicedataset = list
        devicedataset.sortedBy { it.deviceName }
        notifyDataSetChanged()
    }

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO findViewById holen   -----------------


        val deviceName: TextView = itemView.findViewById(R.id.deviceName_TI)
        val deviceTime: TextView = itemView.findViewById(R.id.deviceTime_TI)
        val devicePower: TextView = itemView.findViewById(R.id.devicePower_TI)
        val devicePrice: TextView = itemView.findViewById(R.id.devicePrice_TI)


        val btn_minusDevice: ImageView = itemView.findViewById(R.id.btn_minusDevice)
        val btn_saveDevice: ImageView = itemView.findViewById(R.id.btn_saveDevice)


    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // das itemLayout wird gebaut
        // TODO Schreibe hier deinen Code -------------------

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_item,parent,false)

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
        return devicedataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val deviceItem: Devices = devicedataset[position]
     //   notifyDataSetChanged()


        holder.deviceName.text = deviceItem.deviceName
        holder.deviceTime.text = deviceItem.deviceTime.toString()
        holder.devicePower.text = deviceItem.devicePower.toString()
        holder.devicePrice.text = deviceItem.deviceAmortizationPrice.toString()




        holder.btn_minusDevice.setOnClickListener {
            deviceViewModel.deleteDevice(deviceItem)
        }

        holder.btn_saveDevice.setOnClickListener {

            var editDevice = Devices(

                devicesID = deviceItem.devicesID,
                deviceName = holder.deviceName.text.toString(),
                deviceTime = holder.deviceTime.text.toString().toInt(),  // in Minuten
                devicePower = holder.devicePower.text.toString().toInt(),
                deviceAmortizationPrice = holder.devicePrice.text.toString().toDouble(),
            )
            deviceViewModel.updateDevices(editDevice)
        }




    }
}
