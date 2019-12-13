/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wssc.secureroundtrip.server;
import java.util.Hashtable;
import javax.xml.bind.JAXBElement;

import javax.xml.bind.*;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.Map;

@WebService(endpointInterface="wssc.secureroundtrip.server.IPingService")
public class IPingImpl {

    /* JAX-WS initializes context for each request */
    @Resource
    private WebServiceContext context;

    /* Get Sesssion using well-known key in MessageContext */
    private Map getSession() {
        return (Map)context.getMessageContext()
                .get("com.sun.xml.ws.session");
    }

    /* Get String associated with SessionID for current request */

    private String getSessionData() {
	Map sess = getSession();
        String ret = null;
        if (sess != null){
            ret = (String)sess.get("request_record");
        }
        return ret != null ? ret : "";

    }

    /* Store String associated with SessionID for current request */
    private void setSessionData(String data) {
        Map session = getSession();
        session.put("request_record", data);
    }

    /**
     * @param String
     */
    public String echo(String message) {
        /* append string to session data */
        setSessionData(getSessionData() + " " + message);
        System.out.println("The message is here : " + message);
        return getSessionData();
    }
}