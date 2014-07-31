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
 * on 2014-07-31 at 21:03:50 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.logica.shopping.model;

/**
 * Model definition for ProductoExtVO.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the shopping. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ProductoExtVO extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String codigo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String comercio;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String descripcion;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String detalles;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imagen;

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
  private java.lang.String tipoImagen;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCodigo() {
    return codigo;
  }

  /**
   * @param codigo codigo or {@code null} for none
   */
  public ProductoExtVO setCodigo(java.lang.String codigo) {
    this.codigo = codigo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getComercio() {
    return comercio;
  }

  /**
   * @param comercio comercio or {@code null} for none
   */
  public ProductoExtVO setComercio(java.lang.String comercio) {
    this.comercio = comercio;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion descripcion or {@code null} for none
   */
  public ProductoExtVO setDescripcion(java.lang.String descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDetalles() {
    return detalles;
  }

  /**
   * @param detalles detalles or {@code null} for none
   */
  public ProductoExtVO setDetalles(java.lang.String detalles) {
    this.detalles = detalles;
    return this;
  }

  /**
   * @see #decodeImagen()
   * @return value or {@code null} for none
   */
  public java.lang.String getImagen() {
    return imagen;
  }

  /**

   * @see #getImagen()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodeImagen() {
    return com.google.api.client.util.Base64.decodeBase64(imagen);
  }

  /**
   * @see #encodeImagen()
   * @param imagen imagen or {@code null} for none
   */
  public ProductoExtVO setImagen(java.lang.String imagen) {
    this.imagen = imagen;
    return this;
  }

  /**

   * @see #setImagen()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public ProductoExtVO encodeImagen(byte[] imagen) {
    this.imagen = com.google.api.client.util.Base64.encodeBase64URLSafeString(imagen);
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
  public ProductoExtVO setNombre(java.lang.String nombre) {
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
  public ProductoExtVO setPrecio(java.lang.Float precio) {
    this.precio = precio;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTipoImagen() {
    return tipoImagen;
  }

  /**
   * @param tipoImagen tipoImagen or {@code null} for none
   */
  public ProductoExtVO setTipoImagen(java.lang.String tipoImagen) {
    this.tipoImagen = tipoImagen;
    return this;
  }

  @Override
  public ProductoExtVO set(String fieldName, Object value) {
    return (ProductoExtVO) super.set(fieldName, value);
  }

  @Override
  public ProductoExtVO clone() {
    return (ProductoExtVO) super.clone();
  }

}
