package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.ko.HanjaMappingFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class HanjaMappingFilterFactory extends AbstractTokenFilterFactory {

	public HanjaMappingFilterFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
		super(indexSettings, name, settings);
	}

	@Override
	public TokenStream create(TokenStream tokenStream) {
		return new HanjaMappingFilter(tokenStream);
	}

}
