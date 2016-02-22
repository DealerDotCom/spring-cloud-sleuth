/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.sleuth.autoconfig;

import java.util.Random;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.DefaultSpanNamer;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.TraceKeys;
import org.springframework.cloud.sleuth.sampler.NeverSampler;
import org.springframework.cloud.sleuth.trace.DefaultTracer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Spencer Gibb
 */
@Configuration
@ConditionalOnProperty(value="spring.sleuth.enabled", matchIfMissing=true)
@EnableConfigurationProperties
public class TraceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Random randomForSpanIds() {
		return new Random();
	}

	@Bean
	@ConditionalOnMissingBean
	public Sampler defaultTraceSampler() {
		return NeverSampler.INSTANCE;
	}

	@Bean
	@ConditionalOnMissingBean(Tracer.class)
	public DefaultTracer traceManager(Sampler sampler, Random random,
									ApplicationEventPublisher publisher,
									SpanNamer spanNamer) {
		return new DefaultTracer(sampler, random, publisher, spanNamer);
	}

	@Bean
	@ConditionalOnMissingBean
	public TraceKeys traceKeys() {
		return new TraceKeys();
	}

	@Bean
	@ConditionalOnMissingBean
	public SpanNamer spanNamer() {
		return new DefaultSpanNamer();
	}

}
