/*
*  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.wso2.carbon.messaging;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Test case for headers
 */
public class HeadersTestCase {
    private List<Header> list = new LinkedList<>();

    @BeforeClass(alwaysRun = true)
    public void createHeaders() {
        list.add(new Header("Content-Type", "application/xml"));
        list.add(new Header("POST", "/services/SimpleStockQuoteService HTTP/1.1"));
        list.add(new Header("Connection", "close"));
        list.add(new Header("Set-Cookie", "31abd753ed23r876"));
        list.add(new Header("Set-Cookie", "41abd054ed23r325"));
        list.add(new Header("set-cookie", "51abv054ed23r338"));
    }

    @Test(description = "Creating the headers instance by parsing a list")
    public void createHeadersInstanceTest() {
        Headers headers = new Headers(list);
        Assert.assertEquals(headers.getAll().size(), list.size(), "Headers length mismatched");
    }

    @Test(description = "Testing the get operation")
    public void getTest() {
        Headers headers = new Headers(list);
        Assert.assertEquals(headers.get("Content-Type"), "application/xml", "Not the correct header value");
        Assert.assertEquals(headers.get("content-type"), "application/xml", "Not the correct header value " +
                                                                            "when getting value case insensitively");
        Assert.assertEquals(headers.get("Set-Cookie"), "51abv054ed23r338", "Not the last value of the header");
        Assert.assertNull(headers.get("NonExistingHeader"), "Value must be null for non existing header");
    }

    @Test(description = "Testing the contains operation")
    public void containsTest() {
        Headers headers = new Headers(list);
        Assert.assertTrue(headers.contains("Content-Type"), "Header should be present");
        Assert.assertTrue(headers.contains("content-type"), "Header should be present when checking " +
                                                            "case insensitively");
        Assert.assertTrue(headers.contains("Set-Cookie"), "Header should be present");
        Assert.assertFalse(headers.contains("NonExistingHeader"), "Header should not be present");
    }

    @Test(description = "Testing the getAll operation")
    public void getAllTest() {
        Headers headers = new Headers(list);
        Assert.assertEquals(headers.getAll("Content-Type").size(), 1, "Only one record should exist");
        Assert.assertEquals(headers.getAll("content-type").size(), 1, "Only one record should exist");
        Assert.assertEquals(headers.getAll("content-type").get(0), "application/xml"
                , "Header value not correct");
        Assert.assertEquals(headers.getAll("Content-Type").get(0), "application/xml"
                , "Header value not correct");
        Assert.assertEquals(headers.getAll("Set-Cookie").size(), 3, "List should have 3 elements");
        Assert.assertEquals(headers.getAll("Set-Cookie").get(0), "31abd753ed23r876", "Invalid value");
        Assert.assertEquals(headers.getAll("Set-Cookie").get(1), "41abd054ed23r325", "Invalid value");
        Assert.assertEquals(headers.getAll("Set-Cookie").get(2), "51abv054ed23r338", "Invalid value");
        Assert.assertEquals(headers.getAll("NonExistingHeader").size(), 0
                , "Should be a empty list for nun existing header");
    }

    @Test(description = "Testing the set header operation")
    public void setTest() {
        Headers headers = new Headers(list);
        //setting a single header entry
        headers.set("Content-Type", "application/json");
        Assert.assertEquals(headers.get("Content-Type"), "application/json", "header not updated");
        Assert.assertEquals(headers.getAll().size(), list.size(), "Number of headers changed while " +
                                                                  "setting a single value header");
        //setting a single header entry case insensitively
        headers.set("connection", "keepAlive");
        Assert.assertEquals(headers.get("connection"), "keepAlive", "header not updated case insensitively");
        Assert.assertEquals(headers.getAll().size(), list.size(), "Number of headers changed while " +
                                                                  "setting a single value header");
        //setting a new header
        headers.set("NewHeader", "NewValue");
        Assert.assertEquals(headers.get("NewHeader"), "NewValue", "header not updated");
        Assert.assertEquals(headers.getAll().size(), list.size() + 1, "Number of headers changed while " +
                                                                      "setting a header");
        //setting a header which has multiple entries
        int currentLength = headers.getAll().size();
        headers.set("Set-Cookie", "61abv054ed23r338");
        Assert.assertEquals(headers.get("Set-Cookie"), "61abv054ed23r338", "header not updated");
        Assert.assertEquals(headers.getAll().size(), currentLength - 2, "Number of headers changed while " +
                                                                        "setting a header");
        Assert.assertEquals(headers.get("set-cookie"), "61abv054ed23r338", "header not updated case insensitively");
        Assert.assertEquals(headers.getAll().size(), currentLength - 2, "Number of headers changed while " +
                                                                        "setting a header multivalued header");
    }

    @Test(description = "Updating header by priding header map")
    public void setMapTest() {
        Headers headers = new Headers(list);
        Assert.assertEquals(list.size(), headers.getAll().size(), "Headers length mismatched");
        Map<String , String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("connection", "KeepAlive");
        headerMap.put("Set-Cookie", "71abv054ed23r483");
        headerMap.put("NewHeader", "NewValue");
        headers.set(headerMap);
        Assert.assertEquals(headers.getAll().size(), 10, "Invalid headers list length after updating from map");
        Assert.assertEquals(headers.distinctSize(), 5, "Invalid headers map length after updating from map");
        Assert.assertEquals(headers.get("Content-Type"), "application/json", "Not the correct header value");
        Assert.assertEquals(headers.get("NewHeader"), "NewValue", "Not the correct header value");
        Assert.assertEquals(headers.get("Set-Cookie"), "71abv054ed23r483", "Not the correct header value");
        Assert.assertEquals(headers.get("Connection"), "KeepAlive", "Not the correct header value");
    }

}
