/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2014, FrostWire(R). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.frostwire.search;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frostwire.search.domainalias.DomainAliasManager;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public abstract class PagedWebSearchPerformer extends WebSearchPerformer {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(PagedWebSearchPerformer.class);

    private final int pages;

    public PagedWebSearchPerformer(DomainAliasManager domainAliasManager, long token, String keywords, int timeout, int pages) {
        super(domainAliasManager, token, keywords, timeout);
        this.pages = pages;
    }

    @Override
    public void perform() {
        for (int i = 1; !isStopped() && i <= pages; i++) {
            onResults(this, searchPage(i));
        }
    }

    protected List<? extends SearchResult> searchPage(int page) {
        try {
            String url = getUrl(page, getEncodedKeywords());
            String text = fetchSearchPage(url);
            if (text != null) {
                return searchPage(text);
            }
        } catch (Throwable e) {
            checkAccesibleDomains();
        }
        return Collections.emptyList();
    }

    protected String fetchSearchPage(String url) throws IOException {
        return fetch(url);
    }

    protected abstract String getUrl(int page, String encodedKeywords);

    protected abstract List<? extends SearchResult> searchPage(String page);
}