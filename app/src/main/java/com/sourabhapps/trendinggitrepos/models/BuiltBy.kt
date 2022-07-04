package com.sourabhapps.trendinggitrepos.models

import com.google.gson.annotations.SerializedName
import com.sourabhapps.trendinggitrepos.utilities.AVATAR
import com.sourabhapps.trendinggitrepos.utilities.EMPTY_STRING
import com.sourabhapps.trendinggitrepos.utilities.HREF
import com.sourabhapps.trendinggitrepos.utilities.USERNAME

data class BuiltBy(
		@SerializedName(USERNAME) var username: String? = EMPTY_STRING,
		
		@SerializedName(HREF) var href: String? = EMPTY_STRING,
		
		@SerializedName(AVATAR) var avatar: String? = EMPTY_STRING
)