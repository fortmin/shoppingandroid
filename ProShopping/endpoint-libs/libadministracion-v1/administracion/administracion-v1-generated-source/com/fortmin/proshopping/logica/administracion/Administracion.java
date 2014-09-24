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
 * on 2014-09-24 at 03:06:01 UTC 
 * Modify at your own risk.
 */

package com.fortmin.proshopping.logica.administracion;

/**
 * Service definition for Administracion (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link AdministracionRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Administracion extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the administracion library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://fortminproshop.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "administracion/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Administracion(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Administracion(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "deleteParametroNumero".
   *
   * This request holds the parameters needed by the the administracion server.  After setting any
   * optional parameters, call the {@link DeleteParametroNumero#execute()} method to invoke the remote
   * operation.
   *
   * @param parametro
   * @return the request
   */
  public DeleteParametroNumero deleteParametroNumero(java.lang.String parametro) throws java.io.IOException {
    DeleteParametroNumero result = new DeleteParametroNumero(parametro);
    initialize(result);
    return result;
  }

  public class DeleteParametroNumero extends AdministracionRequest<Void> {

    private static final String REST_PATH = "delete_parametro_numero";

    /**
     * Create a request for the method "deleteParametroNumero".
     *
     * This request holds the parameters needed by the the administracion server.  After setting any
     * optional parameters, call the {@link DeleteParametroNumero#execute()} method to invoke the
     * remote operation. <p> {@link DeleteParametroNumero#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param parametro
     * @since 1.13
     */
    protected DeleteParametroNumero(java.lang.String parametro) {
      super(Administracion.this, "DELETE", REST_PATH, null, Void.class);
      this.parametro = com.google.api.client.util.Preconditions.checkNotNull(parametro, "Required parameter parametro must be specified.");
    }

    @Override
    public DeleteParametroNumero setAlt(java.lang.String alt) {
      return (DeleteParametroNumero) super.setAlt(alt);
    }

    @Override
    public DeleteParametroNumero setFields(java.lang.String fields) {
      return (DeleteParametroNumero) super.setFields(fields);
    }

    @Override
    public DeleteParametroNumero setKey(java.lang.String key) {
      return (DeleteParametroNumero) super.setKey(key);
    }

    @Override
    public DeleteParametroNumero setOauthToken(java.lang.String oauthToken) {
      return (DeleteParametroNumero) super.setOauthToken(oauthToken);
    }

    @Override
    public DeleteParametroNumero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (DeleteParametroNumero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public DeleteParametroNumero setQuotaUser(java.lang.String quotaUser) {
      return (DeleteParametroNumero) super.setQuotaUser(quotaUser);
    }

    @Override
    public DeleteParametroNumero setUserIp(java.lang.String userIp) {
      return (DeleteParametroNumero) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String parametro;

    /**

     */
    public java.lang.String getParametro() {
      return parametro;
    }

    public DeleteParametroNumero setParametro(java.lang.String parametro) {
      this.parametro = parametro;
      return this;
    }

    @Override
    public DeleteParametroNumero set(String parameterName, Object value) {
      return (DeleteParametroNumero) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getParametroNumero".
   *
   * This request holds the parameters needed by the the administracion server.  After setting any
   * optional parameters, call the {@link GetParametroNumero#execute()} method to invoke the remote
   * operation.
   *
   * @param parametro
   * @return the request
   */
  public GetParametroNumero getParametroNumero(java.lang.String parametro) throws java.io.IOException {
    GetParametroNumero result = new GetParametroNumero(parametro);
    initialize(result);
    return result;
  }

  public class GetParametroNumero extends AdministracionRequest<com.fortmin.proshopping.logica.administracion.model.ConfigLong> {

    private static final String REST_PATH = "get_parametro_numero";

    /**
     * Create a request for the method "getParametroNumero".
     *
     * This request holds the parameters needed by the the administracion server.  After setting any
     * optional parameters, call the {@link GetParametroNumero#execute()} method to invoke the remote
     * operation. <p> {@link GetParametroNumero#initialize(com.google.api.client.googleapis.services.A
     * bstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param parametro
     * @since 1.13
     */
    protected GetParametroNumero(java.lang.String parametro) {
      super(Administracion.this, "GET", REST_PATH, null, com.fortmin.proshopping.logica.administracion.model.ConfigLong.class);
      this.parametro = com.google.api.client.util.Preconditions.checkNotNull(parametro, "Required parameter parametro must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetParametroNumero setAlt(java.lang.String alt) {
      return (GetParametroNumero) super.setAlt(alt);
    }

    @Override
    public GetParametroNumero setFields(java.lang.String fields) {
      return (GetParametroNumero) super.setFields(fields);
    }

    @Override
    public GetParametroNumero setKey(java.lang.String key) {
      return (GetParametroNumero) super.setKey(key);
    }

    @Override
    public GetParametroNumero setOauthToken(java.lang.String oauthToken) {
      return (GetParametroNumero) super.setOauthToken(oauthToken);
    }

    @Override
    public GetParametroNumero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetParametroNumero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetParametroNumero setQuotaUser(java.lang.String quotaUser) {
      return (GetParametroNumero) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetParametroNumero setUserIp(java.lang.String userIp) {
      return (GetParametroNumero) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String parametro;

    /**

     */
    public java.lang.String getParametro() {
      return parametro;
    }

    public GetParametroNumero setParametro(java.lang.String parametro) {
      this.parametro = parametro;
      return this;
    }

    @Override
    public GetParametroNumero set(String parameterName, Object value) {
      return (GetParametroNumero) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertParametroNumero".
   *
   * This request holds the parameters needed by the the administracion server.  After setting any
   * optional parameters, call the {@link InsertParametroNumero#execute()} method to invoke the remote
   * operation.
   *
   * @param parametro
   * @param valor
   * @return the request
   */
  public InsertParametroNumero insertParametroNumero(java.lang.String parametro, java.lang.Long valor) throws java.io.IOException {
    InsertParametroNumero result = new InsertParametroNumero(parametro, valor);
    initialize(result);
    return result;
  }

  public class InsertParametroNumero extends AdministracionRequest<Void> {

    private static final String REST_PATH = "insert_parametro_numero";

    /**
     * Create a request for the method "insertParametroNumero".
     *
     * This request holds the parameters needed by the the administracion server.  After setting any
     * optional parameters, call the {@link InsertParametroNumero#execute()} method to invoke the
     * remote operation. <p> {@link InsertParametroNumero#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param parametro
     * @param valor
     * @since 1.13
     */
    protected InsertParametroNumero(java.lang.String parametro, java.lang.Long valor) {
      super(Administracion.this, "POST", REST_PATH, null, Void.class);
      this.parametro = com.google.api.client.util.Preconditions.checkNotNull(parametro, "Required parameter parametro must be specified.");
      this.valor = com.google.api.client.util.Preconditions.checkNotNull(valor, "Required parameter valor must be specified.");
    }

    @Override
    public InsertParametroNumero setAlt(java.lang.String alt) {
      return (InsertParametroNumero) super.setAlt(alt);
    }

    @Override
    public InsertParametroNumero setFields(java.lang.String fields) {
      return (InsertParametroNumero) super.setFields(fields);
    }

    @Override
    public InsertParametroNumero setKey(java.lang.String key) {
      return (InsertParametroNumero) super.setKey(key);
    }

    @Override
    public InsertParametroNumero setOauthToken(java.lang.String oauthToken) {
      return (InsertParametroNumero) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertParametroNumero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertParametroNumero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertParametroNumero setQuotaUser(java.lang.String quotaUser) {
      return (InsertParametroNumero) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertParametroNumero setUserIp(java.lang.String userIp) {
      return (InsertParametroNumero) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String parametro;

    /**

     */
    public java.lang.String getParametro() {
      return parametro;
    }

    public InsertParametroNumero setParametro(java.lang.String parametro) {
      this.parametro = parametro;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long valor;

    /**

     */
    public java.lang.Long getValor() {
      return valor;
    }

    public InsertParametroNumero setValor(java.lang.Long valor) {
      this.valor = valor;
      return this;
    }

    @Override
    public InsertParametroNumero set(String parameterName, Object value) {
      return (InsertParametroNumero) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateParametroNumero".
   *
   * This request holds the parameters needed by the the administracion server.  After setting any
   * optional parameters, call the {@link UpdateParametroNumero#execute()} method to invoke the remote
   * operation.
   *
   * @param parametro
   * @param valor
   * @return the request
   */
  public UpdateParametroNumero updateParametroNumero(java.lang.String parametro, java.lang.Long valor) throws java.io.IOException {
    UpdateParametroNumero result = new UpdateParametroNumero(parametro, valor);
    initialize(result);
    return result;
  }

  public class UpdateParametroNumero extends AdministracionRequest<Void> {

    private static final String REST_PATH = "update_parametro_numero";

    /**
     * Create a request for the method "updateParametroNumero".
     *
     * This request holds the parameters needed by the the administracion server.  After setting any
     * optional parameters, call the {@link UpdateParametroNumero#execute()} method to invoke the
     * remote operation. <p> {@link UpdateParametroNumero#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param parametro
     * @param valor
     * @since 1.13
     */
    protected UpdateParametroNumero(java.lang.String parametro, java.lang.Long valor) {
      super(Administracion.this, "PUT", REST_PATH, null, Void.class);
      this.parametro = com.google.api.client.util.Preconditions.checkNotNull(parametro, "Required parameter parametro must be specified.");
      this.valor = com.google.api.client.util.Preconditions.checkNotNull(valor, "Required parameter valor must be specified.");
    }

    @Override
    public UpdateParametroNumero setAlt(java.lang.String alt) {
      return (UpdateParametroNumero) super.setAlt(alt);
    }

    @Override
    public UpdateParametroNumero setFields(java.lang.String fields) {
      return (UpdateParametroNumero) super.setFields(fields);
    }

    @Override
    public UpdateParametroNumero setKey(java.lang.String key) {
      return (UpdateParametroNumero) super.setKey(key);
    }

    @Override
    public UpdateParametroNumero setOauthToken(java.lang.String oauthToken) {
      return (UpdateParametroNumero) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateParametroNumero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateParametroNumero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateParametroNumero setQuotaUser(java.lang.String quotaUser) {
      return (UpdateParametroNumero) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateParametroNumero setUserIp(java.lang.String userIp) {
      return (UpdateParametroNumero) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String parametro;

    /**

     */
    public java.lang.String getParametro() {
      return parametro;
    }

    public UpdateParametroNumero setParametro(java.lang.String parametro) {
      this.parametro = parametro;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long valor;

    /**

     */
    public java.lang.Long getValor() {
      return valor;
    }

    public UpdateParametroNumero setValor(java.lang.Long valor) {
      this.valor = valor;
      return this;
    }

    @Override
    public UpdateParametroNumero set(String parameterName, Object value) {
      return (UpdateParametroNumero) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Administracion}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Administracion}. */
    @Override
    public Administracion build() {
      return new Administracion(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link AdministracionRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setAdministracionRequestInitializer(
        AdministracionRequestInitializer administracionRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(administracionRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
