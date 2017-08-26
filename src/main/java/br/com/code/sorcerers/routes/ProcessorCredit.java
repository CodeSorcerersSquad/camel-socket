package br.com.code.sorcerers.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;

/**
 * @author victor
 *
 */

public class ProcessorCredit implements Processor {

    private JSONObject jsonObject;

    public ProcessorCredit(){}

    @Override
    public void process(Exchange exchange) throws Exception {
        Double credit = exchange.getIn().getBody(Double.class);
        jsonObject = new JSONObject(exchange.getProperty("jsonObject").toString());
        jsonObject.put("credit", credit);
        exchange.getOut().setBody(jsonObject.toString());
    }
}
