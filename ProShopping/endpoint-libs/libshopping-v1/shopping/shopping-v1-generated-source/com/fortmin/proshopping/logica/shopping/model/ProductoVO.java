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
 * on 2014-08-07 at 20:01:52 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.logica.shopping.model;

/**
 * Model definition for ProductoVO.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the shopping. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ProductoVO extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String comercio;

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
   * @return value or {@code null} for none
   */
  public java.lang.String getComercio() {
    return comercio;
  }

  /**
   * @param comercio comercio or {@code null} for none
   */
  public ProductoVO setComercio(java.lang.String comercio) {
    this.comercio = comercio;
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
  public ProductoVO setNombre(java.lang.String nombre) {
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
  public ProductoVO setPrecio(java.lang.Float precio) {
    this.precio = precio;
    return this;
  }

  @Override
  public ProductoVO set(String fieldName, Object value) {
    return (ProductoVO) super.set(fieldName, value);
  }

  @Override
  public ProductoVO clone() {
    return (ProductoVO) super.clone();
  }

}
