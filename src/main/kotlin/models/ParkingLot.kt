package models

import strategies.FeeCalculationStrategy
import java.time.LocalDateTime

class ParkingLot(numberOfFloors: Int, spotsPerFloor: Int, private val feeCalculationStrategy: FeeCalculationStrategy) {

    private val floors = MutableList(numberOfFloors) { ParkingFloor(it, spotsPerFloor) }
    fun getSpotBySpotNumberAndFloorNumber(spotNumber: Int, floorNumber: Int): ParkingSpot {
        return floors[floorNumber].getSpotById(spotNumber)
    }

    fun calculateBill(ticket: ParkingTicket, exitTime: LocalDateTime): Int {
        println(getSpotBySpotNumberAndFloorNumber(ticket.spotID, ticket.floorNumber).getVehicle())
        return feeCalculationStrategy.getBillAmount(
            ticket.entryTime,
            exitTime,
            getSpotBySpotNumberAndFloorNumber(ticket.spotID, ticket.floorNumber).getVehicle()!!.type
        )
    }

    fun getFirstAvailableSpot(): ParkingSpot? {
        return floors.firstOrNull { floor -> floor.isSpotAvailable() }?.getFirstAvailableSpot()
    }
}
