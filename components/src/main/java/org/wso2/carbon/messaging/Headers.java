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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * This will hold all the headers of incoming message.
 */
public class Headers {
    /**
     * A map that case insensitive to hold the headers.
     */
    private Map<String, String> headerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * A list to hold all the headers without considering the header name case. This will hold
     * duplicated headers as well if incoming message containing duplicated headers.
     */
    private List<Header> headerList = new LinkedList<>();

    public Headers() {
    }

    public Headers(List<Header> list) {
        this.set(list);
    }

    /**
     * To add a header. if the header name is already exist, It will replace the existing header
     * value and if there is duplicated header with same name, remove all of them and add the given
     * header.
     * @param name name of the header field
     * @param value value of the header field
     */
    public void set(String name, String value) {
        headerList.removeIf(header -> header.getName().equalsIgnoreCase(name));
        add(name, value);
    }

    /**
     * This will add all header in given map to the message.
     * @param map map containing header fields
     */
    public void set(final Map<String, String> map) {
        map.forEach(this::add);
    }

    /**
     * This will add all header objects in given list into the message.
     * @param list list containing Header objects
     */
    public void set(final List<Header> list) {
        list.forEach(h -> add(h.getName(), h.getValue()));
    }

    /**
     * This will remove all the headers from the message regardless of the case sensitivity.
     * @param name name of the header to be removed
     */
    public void remove(String name) {
        headerList.removeIf(header -> header.getName().equalsIgnoreCase(name));
        headerMap.remove(name);
    }

    /**
     * This will return the header field value for given name. if there are multiple headers with
     * same name, It will return the value of last header matching the name regardless of
     * case sensitivity of the name of the header.
     * @param name name of the header field
     * @return value of the header for given name
     */
    public String get(String name) {
        return headerMap.get(name);
    }

    /**
     * This will return a LinkedList having all the headers.
     *
     * @return a list containing all the headers fields.
     */
    public List<Header> getAll() {
        return headerList;
    }

    public List<Header> getClone() {
        List<Header> clonedHeaderList = new LinkedList<>();

        headerList.forEach(header -> {
            clonedHeaderList.add(header.getClone());
        });
        return clonedHeaderList;
    }

    /**
     * This will return a values fo given header name regardless of case sensitivity as a LikedList.
     * @param name name of the header
     * @return a list containing the value of given header name regardless of case sensitivity
     */
    public List<String> getAllBy(String name) {
        List<String> hList = headerList.stream().filter(entry -> entry.getName().equalsIgnoreCase(name))
                .map(Header::getValue).collect(Collectors.toCollection(LinkedList::new));
        return hList;
    }

    /**
     * To check whether given header name is exist in the header map regardless of the
     * case sensitivity.
     * @param name name of a header
     * @return true if the header is exist.
     */
    public boolean contains(String name) {
        return headerMap.containsKey(name);
    }

    /**
     * To clear all the header from list.
     */
    public void clear() {
        headerList.clear();
        headerMap.clear();
    }

    /**
     * To get the distinct size of headers.
     * @return distinct size of the headers
     */
    public int distinctSize() {
        return headerMap.size();
    }

    /**
     * To get the  number of of headers in list.
     * @return size of header list
     */
    public int size() {
        return headerList.size();
    }

    private void add(String key, String value) {
        headerList.add(new Header(key, value));
        headerMap.put(key, value);
    }

}
