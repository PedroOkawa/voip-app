package com.okawa.voip.utils.adapter

import android.content.Context

class CountryAdapter(context: Context, regions: List<String>) : SingleArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, regions)