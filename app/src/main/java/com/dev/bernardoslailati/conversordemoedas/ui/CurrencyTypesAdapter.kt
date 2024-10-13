package com.dev.bernardoslailati.conversordemoedas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dev.bernardoslailati.conversordemoedas.R
import com.dev.bernardoslailati.conversordemoedas.databinding.ItemCurrencyTypeBinding
import com.dev.bernardoslailati.conversordemoedas.network.model.CurrencyType

class CurrencyTypesAdapter(
    private val currencyTypes: List<CurrencyType>
) : BaseAdapter() {

    override fun getCount(): Int {
        return currencyTypes.size
    }

    override fun getItem(position: Int): Any {
        return currencyTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return currencyTypes[position].hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return convertView ?: run {
            val item = currencyTypes[position]
            val binding = ItemCurrencyTypeBinding.inflate(LayoutInflater.from(parent?.context))
            with(binding) {
                tvCurrencyAcronym.text = item.acronym
                ivFlag.setImageResource(R.drawable.img_flag_br)
            }
            binding.root
        }
    }

}