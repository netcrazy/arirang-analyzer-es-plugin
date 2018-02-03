package org.elasticsearch.index.analysis;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.ko.KoreanFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class ArirangTokenFilterFactory extends AbstractTokenFilterFactory {

	private boolean bigrammable = false;

	private boolean preserveOrigin = false;

	private boolean preserveCNoun = true;

	private boolean exactMatch = false;
	  
	private boolean queryMode = false;
	  
	private boolean decompound = true;
	
	private boolean preserveVerb = false;
	  
    public ArirangTokenFilterFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        
        this.bigrammable = settings.getAsBoolean("bigrammable", false);
        this.preserveOrigin = settings.getAsBoolean("preserveOrigin", false);
        this.preserveCNoun = settings.getAsBoolean("preserveCNoun", true);
        this.exactMatch = settings.getAsBoolean("exactMatch", false);
        this.queryMode = settings.getAsBoolean("queryMode", false);
        this.decompound = settings.getAsBoolean("decompound", true);
        this.preserveVerb = settings.getAsBoolean("preserveVerb", false);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new KoreanFilter(tokenStream, bigrammable, preserveOrigin, exactMatch, preserveCNoun, queryMode, decompound, preserveVerb);
    }
    
}