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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * To hold the headers of incoming message.
 * This will hold all the headers received to server
 */
public class Headers {
    /**
     *     to hold the header with case sensitivity
     */
    private Map<String, String> headerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * to hold all the headers without considering the header name case and duplication
     */
    private List<Header> headerList = new LinkedList<>();

    public Headers() {
    }

    public Headers(List<Header> list) {
        this.set(list);
    }

    public void set(String name, String value) {
        for (Iterator<Header> headerItr = headerList.iterator(); headerItr.hasNext(); ) {
            Header h = headerItr.next();
            if (h.getName().equalsIgnoreCase(name)) {
                headerItr.remove();
            }
        }
        add(name, value);
    }

    public void set(Map<String, String> map) {
        map.forEach((k, v) -> add(k, v));
    }

    public void set(List<Header> list) {
        list.forEach(h -> add(h.getName(), h.getValue()));
    }

    public void remove(String name) {
        for (Iterator<Header> headerItr = headerList.iterator(); headerItr.hasNext();) {
            Header h = headerItr.next();
            if (h.getName().equalsIgnoreCase(name)) {
                headerItr.remove();
            }
        }
        headerMap.remove(name);
    }

    public String get(String key) {
        return headerMap.get(key);
    }

    public List<Header> getAll() {
        return headerList;
    }

    public List<String> getAll(String key) {
        List<String> hList = headerList.stream().filter(entry -> entry.getName().equalsIgnoreCase(key))
                .map(Header::getValue).collect(Collectors.toCollection(() -> new LinkedList<>()));
        return hList;
    }

    public boolean contains(String name) {
        return headerMap.containsKey(name);
    }

    public void clear() {
        headerList.clear();
        headerMap.clear();
    }

    private void add(String key, String value) {
        headerList.add(new Header(key, value));
        headerMap.put(key, value);
    }

}
