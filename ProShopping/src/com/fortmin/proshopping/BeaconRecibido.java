package com.fortmin.proshopping;

import com.fortmin.proshopapi.ble.Ibeacon;

public class BeaconRecibido {
	private Ibeacon beacon;
	private boolean beacon_leido = false;
	private boolean notificar = false;
	private boolean dispositivoencendido;
	private static int rssi = -47; // con este valor fijo la potencia

	private static BeaconRecibido instancia;

	private BeaconRecibido() {

	}

	public static BeaconRecibido getInstance() {
		if (instancia == null)
			instancia = new BeaconRecibido();
		return instancia;

	}

	public Ibeacon getBeacon() {
		return beacon;
	}

	public void setBeacon(Ibeacon beacon) {
		this.beacon = beacon;
	}

	public String darNombreBeacon() {
		return beacon.getValor_nombre();
	}

	public boolean getEstadoNotificacion() {
		return notificar;
	}

	public void setEstadoNotificacion(boolean notificar) {
		this.notificar = notificar;
	}

	public boolean getBeacon_leido() {
		return beacon_leido;
	}

	public void setBeacon_leido(boolean beacon_leido) {
		this.beacon_leido = beacon_leido;
	}

	public boolean getDispositivoencendido() {
		return dispositivoencendido;
	}

	public void setDispositivoencendido(boolean dispositivoencendido) {
		this.dispositivoencendido = dispositivoencendido;
	}

	public boolean estaCerca() {
		return beacon.clienteCerca();
	}

	public void fijarDistanca() {
		// fija la distacia a detectar el ble
		beacon.setCalibracion(rssi);
	}
}
