package com.fortmin.proshopping.persistencia;

import java.util.Iterator;
import java.util.LinkedList;

import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;

public class PaquetesVO {
	
	private LinkedList<PaqueteVO> paquetes;
	
	public PaquetesVO() {
		paquetes = new LinkedList<PaqueteVO>();
	}
	
	public void agregarPaquete(PaqueteVO paquete) {
		paquetes.addLast(paquete);
	}
	
	public void eliminarPaquete(PaqueteVO paquete) {
		boolean llegue = false;
		if (hasPaquete(paquete.getNombre())) {
			Iterator<PaqueteVO> ipaqs = paquetes.iterator();
			while (ipaqs.hasNext() && !llegue) {
				if (ipaqs.next().getNombre().equals(paquete.getNombre())) llegue = true;
			}
			ipaqs.remove();
		}
	}
	
	public boolean hasPaquete(String nompaq) {
		boolean esta = false;
		Iterator<PaqueteVO> ipaqs = paquetes.iterator();
		while (ipaqs.hasNext() && !esta) {
			if (ipaqs.next().getNombre().equals(nompaq)) esta = true;
		}
		return esta;
	}

}
