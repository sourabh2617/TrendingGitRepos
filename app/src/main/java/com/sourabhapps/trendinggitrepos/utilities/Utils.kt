package com.sourabhapps.trendinggitrepos.utilities

import androidx.annotation.StringRes
import com.sourabhapps.trendinggitrepos.App

object Utils {
	
	fun getString(@StringRes resId: Int): String = App.getContext().getString(resId)
	
	fun getFormattedString(@StringRes resId: Int, vararg args: Any): String = App.getContext().getString(resId, args)
}