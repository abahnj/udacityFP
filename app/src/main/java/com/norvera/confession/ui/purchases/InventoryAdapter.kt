package com.norvera.confession

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.norvera.confession.data.billingrepo.localdb.AugmentedSkuDetails
import kotlinx.android.synthetic.main.inventory_item.view.*

/**
 * This is an [AugmentedSkuDetails] adapter. It can be used anywhere there is a need to display a
 * list of AugmentedSkuDetails. In this app it's used to display both the list of subscriptions and
 * the list of in-app products.
 */
open class InventoryAdapter : RecyclerView.Adapter<InventoryAdapter.AugmentedSkuDetailsViewHolder>() {

    private var skuDetailsList = emptyList<AugmentedSkuDetails>()

    override fun getItemCount() = skuDetailsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AugmentedSkuDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.inventory_item, parent, false)
        return AugmentedSkuDetailsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AugmentedSkuDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(position: Int) = if (skuDetailsList.isEmpty()) null else skuDetailsList[position]

    fun setSkuDetailsList(list: List<AugmentedSkuDetails>) {
        if (list != skuDetailsList) {
            skuDetailsList = list
            notifyDataSetChanged()
        }
    }

    /**
     * In the spirit of keeping simple things simple: this is a friendly way of allowing clients
     * to listen to clicks. You should consider doing this for all your other adapters.
     */
    open fun onSkuDetailsClicked(item: AugmentedSkuDetails) {
        //clients to implement for callback if needed
    }

    inner class AugmentedSkuDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                getItem(adapterPosition)?.let { onSkuDetailsClicked(it) }
            }
        }

        fun bind(item: AugmentedSkuDetails?) {
            item?.apply {
                itemView.apply {
                    val name = title?.substring(0, title.indexOf("("))
                    sku_title.text = name
                    sku_description.text = description
                    sku_price.text = price
                    val drawableId = getSkuDrawableId(sku, this)
                    sku_image.setImageResource(drawableId)
                    isEnabled = canPurchase
                    onDisabled(canPurchase, resources)
                }
            }
        }

        private fun onDisabled(enabled: Boolean, res: Resources) {
            if (enabled) {
                itemView.apply {
                    /* setBackgroundColor(res.getColor(R.color.colorAccent))
                                sku_title.setTextColor(res.getColor(R.color.colorAccent))
                                sku_description.setTextColor(res.getColor(R.color.colorAccent))
                                sku_price.setTextColor(res.getColor(R.color.colorAccent))
                                sku_image.setColorFilter(null)*/
                }
            } else {
                itemView.apply {
                    /*  setBackgroundColor(res.getColor(R.color.colorPrimary))
                                val color = res.getColor(R.color.colorAccent)
                                sku_image.setColorFilter(color)
                                sku_title.setTextColor(color)
                                sku_description.setTextColor(color)
                                sku_price.setTextColor(color)*/
                }
            }
        }

        /**
         *Keeping simple things simple, the icons are named after the SKUs. This way, there is no
         * need to create some elaborate system for matching icons to SKUs when displaying the
         * inventory to users. It is sufficient to do
         *
         * ```
         * sku_image.setImageResource(resources.getIdentifier(sku, "drawable", view.context.packageName))
         *
         * ```
         *
         * Alternatively, in the case where more than one SKU should match the same drawable,
         * you can check with a when{} block. In this sample app, for instance, both gold_monthly and
         * gold_yearly should match the same gold_subs_icon; so instead of keeping two copies of
         * the same icon, when{} is used to set imgName
         */
        private fun getSkuDrawableId(sku: String, view: View): Int {
            val imgName: String = when {
                sku.startsWith("gold_") -> "gold_subs_icon"
                else -> sku
            }
            return view.resources.getIdentifier(imgName, "drawable",
                view.context.packageName)
        }
    }
}