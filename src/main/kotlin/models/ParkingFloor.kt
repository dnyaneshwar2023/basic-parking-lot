package models

class ParkingFloor(val floorNumber: Int, numberOfSpots: Int) {
    val spots = MutableList(numberOfSpots) { ParkingSpot(it) }

}
