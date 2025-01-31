/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.istio.client;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.fabric8.kubernetes.client.Client;
import io.fabric8.kubernetes.client.ExtensionAdapter;
import io.fabric8.kubernetes.client.ExtensionAdapterSupport;
import okhttp3.OkHttpClient;

public class IstioExtensionAdapter extends ExtensionAdapterSupport implements ExtensionAdapter<IstioClient> {

  private static final ConcurrentMap<URL, Boolean> IS_ISTIO = new ConcurrentHashMap<>();
  private static final ConcurrentMap<URL, Boolean> USES_ISTIO_APIGROUPS = new ConcurrentHashMap<>();

  @Override
  public Class<IstioClient> getExtensionType() {
    return IstioClient.class;
  }

  @Override
  public Boolean isAdaptable(Client client) {
    return isAdaptable(client, IS_ISTIO, USES_ISTIO_APIGROUPS, "istio.io");
  }

  @Override
  public IstioClient adapt(Client client) {
    return new DefaultIstioClient(client.adapt(OkHttpClient.class), client.getConfiguration());
  }
}
