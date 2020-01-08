package caja.alanger.cpucooler.views

import android.content.Context
import android.content.pm.PackageInfo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import caja.alanger.cpucooler.R
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.widget.ImageView










class RViewAdapterMain(
    internal var ctx: Context,
    internal var productividadVOList: MutableList<PackageInfo>
) : RecyclerView.Adapter<RViewAdapterMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list_personal_added_item, null, false)

        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            val item = productividadVOList[position]
            val pm = ctx.packageManager
            var ai: ApplicationInfo?
            try {
                ai = pm.getApplicationInfo(item.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                ai = null
            }

            val applicationName = if (ai != null) pm.getApplicationLabel(ai) else "(unknown)"

            holder.item_tViewAppName.text = applicationName


            try{
                val app =item
                val icon = ctx.packageManager.getApplicationIcon(app.packageName)


                    holder.item_iViewAppIcon.setImageDrawable(icon)


            }catch (ne: PackageManager.NameNotFoundException){
                Log.e("recicler",ne.toString())
            }






    }




    override fun getItemCount(): Int {
        return productividadVOList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var item_iViewAppIcon: ImageView
        internal var item_tViewAppName: TextView

        init {

            item_iViewAppIcon = itemView.findViewById(R.id.item_iViewAppIcon)
            item_tViewAppName =
                itemView.findViewById(R.id.item_tViewAppName)

        }
    }
}
