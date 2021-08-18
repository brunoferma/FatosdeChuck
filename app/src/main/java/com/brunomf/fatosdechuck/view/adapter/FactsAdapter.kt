package com.brunomf.fatosdechuck.view.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brunomf.fatosdechuck.R
import com.brunomf.fatosdechuck.service.model.Fact
import com.brunomf.fatosdechuck.view.listener.FactsListener

class FactsAdapter(
    val context: Context,
) : RecyclerView.Adapter<FactsAdapter.FactsViewHolder>() {

    private lateinit var mListener: FactsListener
    private var mFactModelList: List<Fact> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_facts_details,
            parent,
            false
        )
        return FactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        holder.bindView(mFactModelList[position])
    }

    override fun getItemCount(): Int {
        return mFactModelList.size
    }

    fun setFacts(facts: List<Fact>) {
        this.mFactModelList = facts
        updateList()
    }

    fun updateList() {
        notifyDataSetChanged()
    }

    fun attachListener(listener: FactsListener) {
        mListener = listener
    }

    inner class FactsViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val textValue: TextView = itemView.findViewById(R.id.text_value)
        private val textCategory: TextView = itemView.findViewById(R.id.text_category)
        private val imageShare: ImageView = itemView.findViewById(R.id.image_share)

        fun bindView(fact: Fact) {
            if (isLongText(fact.value)) {
                textValue.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.item_recyclerview_fontsize_18sp)
                )
            }
            textValue.text = fact.value
            textCategory.text = formattedCategory(fact.categories)
            imageShare.setOnClickListener {
                mListener.onClickShareImage(fact.url)
            }
        }
    }

    fun formattedCategory(categories: ArrayList<String>): String {
        if (categories.isEmpty()) {
            return "UNCATEGORIZED"
        }
        return categories.toString().replace("[\\[\\]]".toRegex(), "")
    }

    fun isLongText(value: String): Boolean {
        return (value.length > 80)
    }
}
