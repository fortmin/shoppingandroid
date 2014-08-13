/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-08-13 at 17:06:40 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.logica.shopping.model;

/**
 * Model definition for Paquete.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the shopping. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Paquete extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer cantProductos;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String elementoRF;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nombre;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float precio;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> productos;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer puntos;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getCantProductos() {
    return cantProductos;
  }

  /**
   * @param cantProductos cantProductos or {@code null} for none
   */
  public Paquete setCantProductos(java.lang.Integer cantProductos) {
    this.cantProductos = cantProductos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getElementoRF() {
    return elementoRF;
  }

  /**
   * @param elementoRF elementoRF or {@code null} for none
   */
  public Paquete setElementoRF(java.lang.String elementoRF) {
    this.elementoRF = elementoRF;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNombre() {
    return nombre;
  }

  /**
   * @param nombre nombre or {@code null} for none
   */
  public Paquete setNombre(java.lang.String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getPrecio() {
    return precio;
  }

  /**
   * @param precio precio or {@code null} for none
   */
  public Paquete setPrecio(java.lang.Float precio) {
    this.precio = precio;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getProductos() {
    return productos;
  }

  /**
   * @param productos productos or {@code null} for none
   */
  public Paquete setProductos(java.util.List<java.lang.String> productos) {
    this.productos = productos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPuntos() {
    return puntos;
  }

  /**
   * @param puntos puntos or {@code null} for none
   */
  public Paquete setPuntos(java.lang.Integer puntos) {
    this.puntos = puntos;
    return this;
  }

  @Override
  public Paquete set(String fieldName, Object value) {
    return (Paquete) super.set(fieldName, value);
  }

  @Override
  public Paquete clone() {
    return (Paquete) super.clone();
  }

}
