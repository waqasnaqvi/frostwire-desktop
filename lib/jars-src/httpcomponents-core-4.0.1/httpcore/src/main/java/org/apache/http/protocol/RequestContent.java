/*
 * $HeadURL: https://svn.apache.org/repos/asf/httpcomponents/httpcore/tags/4.0.1/httpcore/src/main/java/org/apache/http/protocol/RequestContent.java $
 * $Revision: 744532 $
 * $Date: 2009-02-14 18:12:18 +0100 (Sat, 14 Feb 2009) $
 *
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.protocol;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.ProtocolException;

/**
 * RequestContent is the most important interceptor for outgoing requests. 
 * It is responsible for delimiting content length by adding 
 * <code>Content-Length</code> or <code>Transfer-Content</code> headers based 
 * on the properties of the enclosed entity and the protocol version. 
 * This interceptor is required for correct functioning of client side protocol 
 * processors.

 *
 * @version $Revision: 744532 $
 * 
 * @since 4.0
 */
public class RequestContent implements HttpRequestInterceptor {

    public RequestContent() {
        super();
    }
    
    public void process(final HttpRequest request, final HttpContext context) 
            throws HttpException, IOException {
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (request instanceof HttpEntityEnclosingRequest) {
            if (request.containsHeader(HTTP.TRANSFER_ENCODING)) {
                throw new ProtocolException("Transfer-encoding header already present");
            }
            if (request.containsHeader(HTTP.CONTENT_LEN)) {
                throw new ProtocolException("Content-Length header already present");
            }
            ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
            HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
            if (entity == null) {
                request.addHeader(HTTP.CONTENT_LEN, "0");
                return;
            }
            // Must specify a transfer encoding or a content length 
            if (entity.isChunked() || entity.getContentLength() < 0) {
                if (ver.lessEquals(HttpVersion.HTTP_1_0)) {
                    throw new ProtocolException(
                            "Chunked transfer encoding not allowed for " + ver);
                }
                request.addHeader(HTTP.TRANSFER_ENCODING, HTTP.CHUNK_CODING);
            } else {
                request.addHeader(HTTP.CONTENT_LEN, Long.toString(entity.getContentLength()));
            }
            // Specify a content type if known
            if (entity.getContentType() != null && !request.containsHeader(
                    HTTP.CONTENT_TYPE )) {
                request.addHeader(entity.getContentType()); 
            }
            // Specify a content encoding if known
            if (entity.getContentEncoding() != null && !request.containsHeader(
                    HTTP.CONTENT_ENCODING)) {
                request.addHeader(entity.getContentEncoding()); 
            }
        }
    }
    
}
