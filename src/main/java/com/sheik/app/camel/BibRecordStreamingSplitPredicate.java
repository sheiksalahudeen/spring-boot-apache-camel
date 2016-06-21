package com.sheik.app.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by SheikS on 6/20/2016.
 */
public class BibRecordStreamingSplitPredicate implements Predicate {
    private Logger logger = LoggerFactory.getLogger(BibRecordStreamingSplitPredicate.class);
    private Integer batchSize = 1000;

    public BibRecordStreamingSplitPredicate(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public boolean matches(Exchange exchange) {

        Object camelSplitComplete = exchange.getProperty("CamelSplitComplete");
        if (camelSplitComplete != null && Boolean.TRUE
                .equals(camelSplitComplete)) {
            System.out.println("Processing End Of File: " + exchange.getProperty("CamelFileExchangeFile"));
            return true;
        }
        if (exchange.getProperty("CamelAggregatedSize").equals(batchSize)) {
            return true;
        }

        return false;
    }
}
