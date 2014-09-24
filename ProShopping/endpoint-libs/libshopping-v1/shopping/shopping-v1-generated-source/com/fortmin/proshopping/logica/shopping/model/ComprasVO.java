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
 * on 2014-09-24 at 03:06:13 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.logica.shopping.model;

/**
 * Model definition for ComprasVO.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the shopping. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ComprasVO extends com.google.api.client.json.GenericJson {

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
  private java.lang.String compra;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean entregada;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<PaqueteVO> paquetes;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float precioTotal;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer puntosOtorgados;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getCantItems() {
    return cantItems;
  }

  /**
   * @param cantItems cantItems or {@code null} for none
   */
  public ComprasVO setCantItems(java.lang.Integer cantItems) {
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
  public ComprasVO setCliente(java.lang.String cliente) {
    this.cliente = cliente;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCompra() {
    return compra;
  }

  /**
   * @param compra compra or {@code null} for none
   */
  public ComprasVO setCompra(java.lang.String compra) {
    this.compra = compra;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getEntregada() {
    return entregada;
  }

  /**
   * @param entregada entregada or {@code null} for none
   */
  public ComprasVO setEntregada(java.lang.Boolean entregada) {
    this.entregada = entregada;
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
  public ComprasVO setPaquetes(java.util.List<PaqueteVO> paquetes) {
    this.paquetes = paquetes;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getPrecioTotal() {
    return precioTotal;
  }

  /**
   * @param precioTotal precioTotal or {@code null} for none
   */
  public ComprasVO setPrecioTotal(java.lang.Float precioTotal) {
    this.precioTotal = precioTotal;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPuntosOtorgados() {
    return puntosOtorgados;
  }

  /**
   * @param puntosOtorgados puntosOtorgados or {@code null} for none
   */
  public ComprasVO setPuntosOtorgados(java.lang.Integer puntosOtorgados) {
    this.puntosOtorgados = puntosOtorgados;
    return this;
  }

  @Override
  public ComprasVO set(String fieldName, Object value) {
    return (ComprasVO) super.set(fieldName, value);
  }

  @Override
  public ComprasVO clone() {
    return (ComprasVO) super.clone();
  }

}
