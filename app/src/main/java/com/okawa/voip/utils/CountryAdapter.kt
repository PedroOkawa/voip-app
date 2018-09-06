package com.okawa.voip.utils

import android.content.Context
import com.okawa.voip.model.CountryCode

class CountryAdapter(context: Context, countryCodes: List<CountryCode>) : SingleArrayAdapter<CountryCode>(context, android.R.layout.simple_spinner_dropdown_item, countryCodes) {

}