package com.sheik.app.camel;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SheikS on 6/20/2016.
 */
public class BibRecordStreamingBodyAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            oldExchange = new DefaultExchange(newExchange);
            oldExchange.getIn().setHeaders(newExchange.getIn().getHeaders());
            List<Object> body = new ArrayList<Object>();
            oldExchange.getIn().setBody(body);
            oldExchange.getExchangeId();
        }
        oldExchange.getIn().getBody(List.class).add(newExchange.getIn().getBody());

        for (String key : newExchange.getProperties().keySet()) {
            oldExchange.setProperty(key, newExchange.getProperty(key));
        }

        return oldExchange;
    }
}
