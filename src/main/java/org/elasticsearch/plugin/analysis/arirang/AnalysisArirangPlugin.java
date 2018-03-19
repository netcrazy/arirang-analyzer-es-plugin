package org.elasticsearch.plugin.analysis.arirang;

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


import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.index.analysis.ArirangTokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.rest.action.analysis.ArirangAnalyzerRestAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Collections.singletonMap;
import static java.util.Collections.singletonList;

public class AnalysisArirangPlugin extends Plugin implements AnalysisPlugin,ActionPlugin {

    @Override
    public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
    	Map<String, AnalysisProvider<TokenFilterFactory>> filters = new HashMap<>();
    	
    	filters.put("arirang_filter", ArirangTokenFilterFactory::new);
    	filters.put("arirang_hanja_filter", HanjaMappingFilterFactory::new);
    	filters.put("arirang_rm_punc_filter", RemovePunctuationFilterFactory::new);
    	
        return filters;
    }

    @Override
    public Map<String, AnalysisProvider<TokenizerFactory>> getTokenizers() {
        Map<String, AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();
        extra.put("arirang_tokenizer", ArirangTokenizerFactory::new);

        return extra;
    }

    @Override
    public Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return singletonMap("arirang_analyzer", ArirangAnalyzerProvider::new);
    }
    
	@Override
	public List<RestHandler> getRestHandlers(final Settings settings, final RestController restController,
			final ClusterSettings clusterSettings, final IndexScopedSettings indexScopedSettings,
			final SettingsFilter settingsFilter, final IndexNameExpressionResolver indexNameExpressionResolver,
			final Supplier<DiscoveryNodes> nodesInCluster) {

		return singletonList(new ArirangAnalyzerRestAction(settings, restController));
	}
}