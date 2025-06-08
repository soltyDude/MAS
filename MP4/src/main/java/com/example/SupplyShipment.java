package com.example;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A cargo spacecraft delivery containing multiple ResourceTransfers.
 *
 * MP4 construct: OWN BUSINESS CONSTRAINT
 *     – total payload (sum of kgMoved in all transfers) must not exceed MAX_PAYLOAD_KG.
 */
public class SupplyShipment implements Serializable {

    private static final long serialVersionUID = 1L;

    /* ---------- business constant ---------- */
    public static final double MAX_PAYLOAD_KG = 10_000.0;

    /* ---------- class extent ---------- */
    private static final List<SupplyShipment> EXTENT = new ArrayList<>();

    public static List<SupplyShipment> getExtent() {
        return Collections.unmodifiableList(new ArrayList<SupplyShipment>(EXTENT));
    }

    /* ---------- attributes ---------- */
    private final String      shipmentNo;      // business id (may be unique in DB)
    private final LocalDate   arrivalDate;
    private final List<ResourceTransfer> transfers = new ArrayList<>();

    /* ---------- constructor ---------- */
    public SupplyShipment(String shipmentNo, LocalDate arrivalDate) {
        this.shipmentNo  = Objects.requireNonNull(shipmentNo,  "shipmentNo null");
        this.arrivalDate = Objects.requireNonNull(arrivalDate, "arrivalDate null");
        EXTENT.add(this);
    }

    /* ---------- business-constraint logic ---------- */

    /**
     * Adds a transfer to this shipment,
     * checking that total weight stays ≤ MAX_PAYLOAD_KG.
     *
     * @throws IllegalStateException if payload limit would be exceeded
     */
    public void addTransfer(ResourceTransfer rt) {
        Objects.requireNonNull(rt, "transfer null");

        double currentTotal =
                transfers.stream()
                        .mapToDouble(ResourceTransfer::getKgMoved)
                        .sum();

        double newTotal = currentTotal + rt.getKgMoved();
        if (newTotal > MAX_PAYLOAD_KG) {
            throw new IllegalStateException(
                    "Payload limit exceeded: " + newTotal + " kg > " + MAX_PAYLOAD_KG);
        }
        transfers.add(rt);
    }

    /* ---------- queries ---------- */

    public String getShipmentNo() {
        return shipmentNo;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public double getTotalPayloadKg() {
        return transfers.stream()
                .mapToDouble(ResourceTransfer::getKgMoved)
                .sum();
    }

    public List<ResourceTransfer> getTransfers() {
        return Collections.unmodifiableList(new ArrayList<ResourceTransfer>(transfers));
    }

    /* ---------- equality by shipmentNo ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplyShipment)) return false;
        SupplyShipment that = (SupplyShipment) o;
        return shipmentNo.equalsIgnoreCase(that.shipmentNo);
    }

    @Override
    public int hashCode() {
        return shipmentNo.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "SupplyShipment{" + shipmentNo +
                ", arrival=" + arrivalDate +
                ", payload=" + getTotalPayloadKg() + " kg}";
    }
}
