package kep.mobile.common.domain.model

data class About(val info: String, val website: String, val twitter: String, val location: Location)
data class Location(val name: String, val latitude: Double, val longitude: Double)