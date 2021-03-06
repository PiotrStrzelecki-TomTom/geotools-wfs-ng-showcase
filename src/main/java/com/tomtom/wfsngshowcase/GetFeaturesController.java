package com.tomtom.wfsngshowcase;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.geotools.data.DataStore;
import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.util.factory.GeoTools;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.IncludeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetFeaturesController {

    private final FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(GeoTools.getDefaultHints());
    private DataStore dataStore;

    @Autowired
    public GetFeaturesController(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping(
        path = "getFeatures/{typeName}/{wktGeom}",
        produces = "application/json")
    public List<FeatureDto> getFeatures(
        @PathVariable String typeName,
        @PathVariable String wktGeom) throws IOException, CQLException {

        final SimpleFeatureSource eventsSource = dataStore.getFeatureSource(typeName);
        final String geometryColumn = dataStore.getSchema(typeName).getGeometryDescriptor().getLocalName();
        final Filter filter = filterOf(geometryColumn, wktGeom);
        final SimpleFeatureCollection eventsFeatures = eventsSource.getFeatures(new Query(typeName, filter));
        final SimpleFeatureIterator features = eventsFeatures.features();

        List<FeatureDto> featureDtos = new LinkedList<>();
        try {
            while (features.hasNext()) {
                final SimpleFeature next = features.next();

                final FeatureDto featureDto = new FeatureDto(
                    next.getID(),
                    next.getAttribute("cat").toString(),
                    next.getDefaultGeometry().toString());

                featureDtos.add(featureDto);
            }
        } finally {
            features.close();
        }

        return featureDtos;
    }

    private Filter filterOf(String geometryColumn, String wktGeom) throws CQLException {
        Filter filter = IncludeFilter.INCLUDE;

        final Filter geomFilter = CQL.toFilter(String.format("INTERSECTS(%s,%s)", geometryColumn, wktGeom));
        filter = ff.and(filter, geomFilter);

        return filter;
    }

}
