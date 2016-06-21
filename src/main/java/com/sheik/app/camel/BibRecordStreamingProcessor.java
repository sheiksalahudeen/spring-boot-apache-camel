package com.sheik.app.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by SheikS on 6/20/2016.
 */
public class BibRecordStreamingProcessor implements Processor {
    private static Logger logger = LoggerFactory.getLogger(BibRecordStreamingProcessor.class);
    private int batchCount = 1;
    private int recordIndex = 1;

    public BibRecordStreamingProcessor() {

    }

    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO : :Process Records+
        StringBuilder stringBuilder = new StringBuilder();
        if (exchange.getIn().getBody() instanceof List) {
            System.out.println("Record Size : " + ((List<String>) ((List) exchange.getIn().getBody())).size());
            for (String content : (List<String>) exchange.getIn().getBody()) {
                stringBuilder.append(content);
            }
        }
    }
}
