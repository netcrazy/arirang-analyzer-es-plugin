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

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

import java.io.IOException;

public class ArirangAnalyzerProvider extends AbstractIndexAnalyzerProvider<KoreanAnalyzer> {

    private final KoreanAnalyzer analyzer;

    private boolean bigrammable = false;
    
    private boolean preserveOrigin = false;
      
    private boolean exactMatch = false;
    private boolean preserveCNoun = true;
    private boolean queryMode = false;
    private boolean wordSegment = false;
    private boolean decompound = true;
    private boolean removePunctuation = false;
    private boolean preserveVerb = false;
    
    public ArirangAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) throws IOException {
        super(indexSettings, name, settings);

        this.bigrammable = settings.getAsBoolean("bigrammable", false);
        this.preserveOrigin = settings.getAsBoolean("preserveOrigin", false);
        this.preserveCNoun = settings.getAsBoolean("preserveCNoun", true);
        this.exactMatch = settings.getAsBoolean("exactMatch", false);
        this.queryMode = settings.getAsBoolean("queryMode", false);
        this.decompound = settings.getAsBoolean("decompound", true);
        this.wordSegment = settings.getAsBoolean("wordSegment", false);
        this.removePunctuation = settings.getAsBoolean("removePunctuation", false);
        this.preserveVerb = settings.getAsBoolean("preserveVerb", false);
        
        analyzer = new KoreanAnalyzer();
        
        analyzer.setBigrammable(this.bigrammable);
        analyzer.setHasOrigin(this.preserveOrigin);
        analyzer.setOriginCNoun(this.preserveCNoun);
        analyzer.setQueryMode(this.queryMode);
        analyzer.setWordSegment(this.wordSegment);
        analyzer.setDecompound(this.decompound);
        analyzer.setExactMatch(this.exactMatch);
        analyzer.setRemovePunctuation(this.removePunctuation);
        analyzer.setHasVerb(this.preserveVerb);
    }

    @Override
    public KoreanAnalyzer get() {
        return this.analyzer;
    }
}