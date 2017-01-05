/*
 * Copyright 2014 NAVER Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.navercorp.pinpoint.plugin.hystrix;

import com.navercorp.pinpoint.common.trace.TraceMetadataProvider;
import com.navercorp.pinpoint.common.trace.TraceMetadataSetupContext;

/**
 * @author Jiaqi Feng
 * @author HyunGil Jeong
 *
 */
public class HystrixTraceMetadataProvider implements TraceMetadataProvider {

    /* (non-Javadoc)
     * @see com.navercorp.pinpoint.common.trace.TraceMetadataProvider#setup(com.navercorp.pinpoint.common.trace.TraceMetadataSetupContext)
     */
    @Override
    public void setup(TraceMetadataSetupContext context) {
        context.addServiceType(HystrixPluginConstants.HYSTRIX_SERVICE_TYPE);
        context.addServiceType(HystrixPluginConstants.HYSTRIX_INTERNAL_SERVICE_TYPE);

        context.addAnnotationKey(HystrixPluginConstants.HYSTRIX_COMMAND_ANNOTATION_KEY);
        context.addAnnotationKey(HystrixPluginConstants.HYSTRIX_COMMAND_EXECUTION_ANNOTATION_KEY);
        context.addAnnotationKey(HystrixPluginConstants.HYSTRIX_FALLBACK_CAUSE_ANNOTATION_KEY);
    }
}
