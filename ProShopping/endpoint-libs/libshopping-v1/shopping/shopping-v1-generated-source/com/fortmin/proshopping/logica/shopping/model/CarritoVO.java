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
 * on 2014-08-21 at 20:29:05 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.logica.shopping.model;

/**
 * Model definition for CarritoVO.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the shopping. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class CarritoVO extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer cantItems;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String cliente;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> listaPaquetes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<PaqueteVO> paquetes;

  static {
    // hack to force ProGuard to consider PaqueteVO used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(PaqueteVO.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float precioCarrito;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer puntosCarrito;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getCantItems() {
    return cantItems;
  }

  /**
   * @param cantItems cantItems or {@code null} for none
   */
  public CarritoVO setCantItems(java.lang.Integer cantItems) {
    this.cantItems = cantItems;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCliente() {
    return cliente;
  }

  /**
   * @param cliente cliente or {@code null} for none
   */
  public CarritoVO setCliente(java.lang.String cliente) {
    this.cliente = cliente;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getListaPaquetes() {
    return listaPaquetes;
  }

  /**
   * @param listaPaquetes listaPaquetes or {@code null} for none
   */
  public CarritoVO setListaPaquetes(java.util.List<java.lang.String> listaPaquetes) {
    this.listaPaquetes = listaPaquetes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<PaqueteVO> getPaquetes() {
    return paquetes;
  }

  /**
   * @param paquetes paquetes or {@code null} for none
   */
  public CarritoVO setPaquetes(java.util.List<PaqueteVO> paquetes) {
    this.paquetes = paquetes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getPrecioCarrito() {
    return precioCarrito;
  }

  /**
   * @param precioCarrito precioCarrito or {@code null} for none
   */
  public CarritoVO setPrecioCarrito(java.lang.Float precioCarrito) {
    this.precioCarrito = precioCarrito;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPuntosCarrito() {
    return puntosCarrito;
  }

  /**
   * @param puntosCarrito puntosCarrito or {@code null} for none
   */
  public CarritoVO setPuntosCarrito(java.lang.Integer puntosCarrito) {
    this.puntosCarrito = puntosCarrito;
    return this;
  }

  @Override
  public CarritoVO set(String fieldName, Object value) {
    return (CarritoVO) super.set(fieldName, value);
  }

  @Override
  public CarritoVO clone() {
    return (CarritoVO) super.clone();
  }

}
