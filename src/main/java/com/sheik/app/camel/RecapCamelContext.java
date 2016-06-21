package com.sheik.app.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Created by SheikS on 6/20/2016.
 */
public class RecapCamelContext {
    private static RecapCamelContext recapCamelContext;
    private CamelContext context;

    public static RecapCamelContext getInstance() {
        if(null == recapCamelContext) {
            recapCamelContext = new RecapCamelContext();
        }
        return recapCamelContext;
    }

    private RecapCamelContext() {
        context = new DefaultCamelContext();
        try {
            context.start();
        } catch (Exception e) {
            System.out.println("Camel Context not initialized");
            e.printStackTrace();
        }
    }

    public void addRoutes(RouteBuilder routeBuilder) throws Exception {
        getContext().addRoutes(routeBuilder);
    }

    public void addDynamicBibRecordStreamRoute(RecapCamelContext camelContext, String endPointFrom, int chunkSize) throws Exception {
        camelContext.addRoutes(new BibRecordRouteBuilder(camelContext.getContext(), endPointFrom, chunkSize));
    }

    public CamelContext getContext() {
        return context;
    }

    public void setContext(CamelContext context) {
        this.context = context;
    }
}
