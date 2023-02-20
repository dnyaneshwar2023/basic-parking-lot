package models

class ParkingFloor(val floorNumber: Int, numberOfSpots: Int) {
    val spots = MutableList(numberOfSpots) { ParkingSpot(it, floorNumber) }

    fun getFirstAvailableSpot(): ParkingSpot? {
        return spots.firstOrNull() { spot -> spot.isAvailable() }
    }

}
