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
 * (build: 2014-07-09 17:08:39 UTC)
 * on 2014-07-17 at 01:45:23 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.shopping.model;

/**
 * Model definition for Mensaje.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the shopping. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Mensaje extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String mensaje;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String operacion;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMensaje() {
    return mensaje;
  }

  /**
   * @param mensaje mensaje or {@code null} for none
   */
  public Mensaje setMensaje(java.lang.String mensaje) {
    this.mensaje = mensaje;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOperacion() {
    return operacion;
  }

  /**
   * @param operacion operacion or {@code null} for none
   */
  public Mensaje setOperacion(java.lang.String operacion) {
    this.operacion = operacion;
    return this;
  }

  @Override
  public Mensaje set(String fieldName, Object value) {
    return (Mensaje) super.set(fieldName, value);
  }

  @Override
  public Mensaje clone() {
    return (Mensaje) super.clone();
  }

}
