package models

class ParkingSpot(private val spotID: Int, private val floorNumber: Int) {
    private var vehicle: Vehicle? = null
    fun setVehicle(vehicle: Vehicle) {
        this.vehicle = vehicle
    }

    fun getSpotID(): Int {
        return spotID
    }

    fun getFloorNumber(): Int {
        return floorNumber
    }

    fun getVehicle(): Vehicle? {
        return vehicle
    }

    fun removeVehicle() {
        this.vehicle = null
    }

    fun isAvailable(): Boolean {
        return this.vehicle == null
    }
}
