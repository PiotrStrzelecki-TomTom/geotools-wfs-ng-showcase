package com.tomtom.wfsngshowcase;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.internal.WFSClient;
import org.geotools.data.wfs.internal.WFSConfig;
import org.geotools.http.HTTPClient;
import org.geotools.http.commons.MultithreadedHttpClientFactory;
import org.geotools.ows.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebAppConfig {

    @Bean
    DataStore createDataStore(@Value("${geoserver.url}") final String geoserverUrl) throws IOException, ServiceException {
        final URL wfsUrl = new URL(geoserverUrl);
        final WFSConfig wfsConfig = WFSConfig.fromParams(Map.of());
        final MultithreadedHttpClientFactory factory = new MultithreadedHttpClientFactory();
        final HTTPClient httpClient = factory.createClient(Collections.emptyList());
        return new WFSDataStore(new WFSClient(wfsUrl, httpClient, wfsConfig));
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }

}
