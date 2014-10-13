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
 * on 2014-10-13 at 18:54:36 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.logica.shopping.model;

/**
 * Model definition for PaqueteVO.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the shopping. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PaqueteVO extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer cantProductos;

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
  private java.util.List<ProductoVO> productos;

  static {
    // hack to force ProGuard to consider ProductoVO used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(ProductoVO.class);
  }

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
  public PaqueteVO setCantProductos(java.lang.Integer cantProductos) {
    this.cantProductos = cantProductos;
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
  public PaqueteVO setNombre(java.lang.String nombre) {
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
  public PaqueteVO setPrecio(java.lang.Float precio) {
    this.precio = precio;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<ProductoVO> getProductos() {
    return productos;
  }

  /**
   * @param productos productos or {@code null} for none
   */
  public PaqueteVO setProductos(java.util.List<ProductoVO> productos) {
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
  public PaqueteVO setPuntos(java.lang.Integer puntos) {
    this.puntos = puntos;
    return this;
  }

  @Override
  public PaqueteVO set(String fieldName, Object value) {
    return (PaqueteVO) super.set(fieldName, value);
  }

  @Override
  public PaqueteVO clone() {
    return (PaqueteVO) super.clone();
  }

}
